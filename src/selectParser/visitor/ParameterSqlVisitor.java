package selectParser.visitor;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import selectParser.parser.ASTAnd;
import selectParser.parser.ASTOr;
import selectParser.parser.ASTPlainSelect;
import selectParser.parser.ASTSelect;
import selectParser.parser.ASTUDFFunc;
import selectParser.parser.ASTUnion;
import selectParser.parser.Node;

public class ParameterSqlVisitor extends SqlStringVisitor {
	private Map<String,UDFFunction> udfFunctions = new HashMap<String, UDFFunction>();

	private SqlConditionExtractor extractor;

	
	/**
	 * 解析后的参数
	 */
	private List<Object> parameters;
	
	private boolean doInParameter(Runnable r) {
		int pBuffer = buffer.length();
		int pParameters = parameters.size();
		
		try {
			r.run();
			
			return true;
		} catch (ParameterHasNoValueException e) {
			buffer.setLength(pBuffer);

			while (parameters.size() > pParameters) {
				parameters.remove(parameters.size() - 1);
			}
			
			return false;
		}
	}

	public ParameterSqlVisitor(StringBuffer sqlString, List<Object> parameters, boolean count) {
		super(sqlString);
		
		this.parameters = parameters;
		this.count = count;
	}

	static final private String andStr = " and ";

	public Object visit(ASTAnd node, final Object data) {
		int conditionNum = 0;

		// 增加ParameterHasNoValue, ParameterNotFoundException的处理逻辑
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			final Node c = (Node) node.jjtGetChild(i);

			Runnable r = new Runnable() {
				public void run() {
					boolean isor = false;
					
					if (c instanceof ASTOr) {
						isor = true;
					}
					
					// 如果字节点是or,则加括号
					if (isor) {
						buffer.append("(");
					}
					
					c.jjtAccept(ParameterSqlVisitor.this, data);
					
					if (isor) {
						buffer.append(")");
					}

					buffer.append(andStr);
				}
			};
			
			boolean parameterValid = doInParameter(r);
			
			if (parameterValid) {
				conditionNum++;
			}
		}
		
		// 去掉最后的and
		if (conditionNum > 0) {
			buffer.setLength(buffer.length() - andStr.length());
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Object visit(ASTUDFFunc func, Object data) {
		Map funcParams = func.getParameters();
		String name = func.getName();
		UDFFunction funcInst = (UDFFunction) udfFunctions.get(name);
		if (funcInst == null) {
			throw new RuntimeException("没有找到函数" + name);
		}
		try {
			Map newParam = new HashMap();
			
			Iterator it = funcParams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if (value != null && value.startsWith("\"")
						&& value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}
				newParam.put(key, value);
			}

			Object obj = funcInst.invoke(newParam);
			if (obj != null && obj.getClass().isArray()) {
				int length = Array.getLength(obj);
				for (int i = 0; i < length; i++) {
					Object ele = Array.get(obj, i);
					buffer.append(" ? ");
					if (i < length - 1) {
						buffer.append(", ");
					}

					parameters.add(ele);
				}
			} else {
				buffer.append(" ? ");
				parameters.add(obj);
			}
		} catch (ParameterHasNoValueException e) {
			// 函数没有取到参数值
			throw e;

		} catch (ParameterNotFoundException e) {
			// 参数是必须的，但是没有指定
			throw e;
		}
		return null;
	}

	public void genWhereSql(final ASTPlainSelect plainSelect, final Object data, final boolean isTopSelect) {
		boolean hasWhere = false;
		if (plainSelect.getWhere() != null) {
			String whereStr = " WHERE ";
			buffer.append(whereStr);
			int pBuffer = buffer.length();
			Runnable r = new Runnable() {

				public void run() {
					plainSelect.getWhere().jjtAccept(ParameterSqlVisitor.this, data);
				}
			};
			doInParameter(r);

			// 如果有条件
			if (buffer.length() > pBuffer) {
				hasWhere = true;
			} else {
				hasWhere = false;
				buffer.setLength(buffer.length() - whereStr.length());
			}
		}
		
		boolean isTopUnionSelect = plainSelect.jjtGetParent() instanceof ASTUnion
				&& plainSelect.jjtGetParent().jjtGetParent() instanceof ASTSelect;
		
		// 是顶层的select语句，而不是子查询，则使用SqlConditionMaker添加额外的条件
		if (extractor != null && (isTopSelect || isTopUnionSelect)) {
			StringBuffer conditionBuffer = new StringBuffer();
			extractor.doExtract(conditionBuffer, parameters);
			
			if (conditionBuffer.length() > 0) {
				if (hasWhere) {
					buffer.append(" and (");
					buffer.append(conditionBuffer);
					buffer.append(")");
				} else {
					buffer.append(" where ");
					buffer.append(conditionBuffer);
				}
			}
		}
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, UDFFunction> getUdfFunctions() {
		return udfFunctions;
	}

	public void setUdfFunctions(Map<String, UDFFunction> udfFunctions) {
		this.udfFunctions = udfFunctions;
	}

	public SqlConditionExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(SqlConditionExtractor extractor) {
		this.extractor = extractor;
	}
}
