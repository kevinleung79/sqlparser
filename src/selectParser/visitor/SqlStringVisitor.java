package selectParser.visitor;

import java.util.ArrayList;
import java.util.List;

import selectParser.parser.ASTAdd;
import selectParser.parser.ASTAllCol;
import selectParser.parser.ASTAllTableCol;
import selectParser.parser.ASTAllTableColumns;
import selectParser.parser.ASTAnd;
import selectParser.parser.ASTBetween;
import selectParser.parser.ASTCaseWhenExpression;
import selectParser.parser.ASTColumn;
import selectParser.parser.ASTColumnIndex;
import selectParser.parser.ASTColumnReference;
import selectParser.parser.ASTDistinct;
import selectParser.parser.ASTDouble;
import selectParser.parser.ASTExistsExpression;
import selectParser.parser.ASTExpression;
import selectParser.parser.ASTFromItem;
import selectParser.parser.ASTFromItemsList;
import selectParser.parser.ASTFunction;
import selectParser.parser.ASTHaving;
import selectParser.parser.ASTInExpression;
import selectParser.parser.ASTInteger;
import selectParser.parser.ASTInverseExpression;
import selectParser.parser.ASTIsNullExpression;
import selectParser.parser.ASTJdbcParameter;
import selectParser.parser.ASTJoinerExpression;
import selectParser.parser.ASTJoinsList;
import selectParser.parser.ASTLikeExpression;
import selectParser.parser.ASTMult;
import selectParser.parser.ASTNull;
import selectParser.parser.ASTOr;
import selectParser.parser.ASTOrderByElement;
import selectParser.parser.ASTOrderByElements;
import selectParser.parser.ASTPlainSelect;
import selectParser.parser.ASTRegularCondition;
import selectParser.parser.ASTSelect;
import selectParser.parser.ASTSelectExpressionItem;
import selectParser.parser.ASTSelectItemsList;
import selectParser.parser.ASTSimpleExpressionList;
import selectParser.parser.ASTString;
import selectParser.parser.ASTSubSelect;
import selectParser.parser.ASTTable;
import selectParser.parser.ASTTop;
import selectParser.parser.ASTUnion;
import selectParser.parser.ASTWhenThenSearchCondition;
import selectParser.parser.ASTWhenThenValue;
import selectParser.parser.ASTWhereClause;
import selectParser.parser.Node;

public class SqlStringVisitor extends DefaultParserVisitor {
	protected StringBuffer buffer;

	protected boolean count = false;

	public SqlStringVisitor(StringBuffer buffer) {
		this(buffer, false);
	}

	public SqlStringVisitor(StringBuffer buffer, boolean count) {
		this.buffer = buffer;
		this.count = count;
	}

	public Object visit(ASTUnion node, Object data) {
		List<Node> selects = new ArrayList<Node>();

		ASTOrderByElements orderByElements = null;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Node c = node.jjtGetChild(i);

			if (c instanceof ASTPlainSelect) {
				selects.add(c);
			} else {
				orderByElements = (ASTOrderByElements) c;
			}
		}

		for (int i = 0; i < selects.size(); i++) {
			ASTPlainSelect plainSelect = (ASTPlainSelect) selects.get(i);

			buffer.append("(");
			plainSelect.jjtAccept(this, data);
			buffer.append(")\n");

			if (i < selects.size() - 1) {
				buffer.append(" union ");

				if (node.isAll()) {
					buffer.append(" all \n");
				} else {
					buffer.append("\n");
				}
			}
		}

		if (orderByElements != null) {
			buffer.append(" ORDER BY ");

			for (int i = 0; i < orderByElements.jjtGetNumChildren(); i++) {
				Node orderByElement = orderByElements.jjtGetChild(i);

				orderByElement.jjtAccept(this, data);

				if (i < orderByElement.jjtGetNumChildren() - 1) {
					buffer.append(", ");
				}
			}
		}

		return null;
	}

	public Object visit(ASTColumnIndex node, Object data) {
		String index = (String) node.jjtGetValue();
		buffer.append(index);

		return null;
	}

	public Object visit(ASTColumnReference node, Object data) {
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTAllCol node, Object data) {
		buffer.append(" * ");

		return null;
	}

	public Object visit(ASTSimpleExpressionList node, Object data) {
		buffer.append("(");

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Node parameter = node.jjtGetChild(i);

			parameter.jjtAccept(this, data);

			if (i < node.jjtGetNumChildren() - 1) {
				buffer.append(", ");
			}
		}

		buffer.append(")");

		return null;
	}

	public Object visit(ASTFunction function, Object data) {
		buffer.append(function.getName());

		if (function.isAllColumns()) {
			buffer.append("(*)");
		} else {
			if (function.jjtGetNumChildren() == 0) {
				buffer.append("(");
			}

			function.childrenAccept(this, data);

			if (function.jjtGetNumChildren() == 0) {
				buffer.append(")");
			}
		}

		return null;
	}

	public Object visit(ASTSelect select, Object data) {
		return select.childrenAccept(this, data);
	}

	public Object visit(ASTPlainSelect plainSelect, Object data) {
		boolean isTopSelect = plainSelect.jjtGetParent() instanceof ASTSelect;
		
		genFullSelSql(plainSelect, data, isTopSelect);

		genWhereSql(plainSelect, data, isTopSelect);

		genGroupBy(plainSelect, data, isTopSelect);

		genOrderBy(plainSelect, data, isTopSelect);

		return null;
	}

	public void genFullSelSql(ASTPlainSelect plainSelect, Object data, boolean isTopSelect) {
		buffer.append("SELECT ");

		genTopSql(plainSelect);
		genDistinctSql(plainSelect, data);
		genSelectSql(plainSelect, data, isTopSelect);
		genFromSql(plainSelect, data);
	}

	public void genOrderBy(ASTPlainSelect plainSelect, Object data, boolean isTopSelect) {
		if (plainSelect.getOrderBy() != null) {
			ASTOrderByElements orderByElements = plainSelect.getOrderBy();

			buffer.append(" ORDER BY ");

			for (int i = 0; i < orderByElements.jjtGetNumChildren(); i++) {
				Node orderByElement = orderByElements.jjtGetChild(i);
				orderByElement.jjtAccept(this, data);
				if (i < orderByElements.jjtGetNumChildren() - 1) {
					buffer.append(", ");
				}
			}
		}
	}

	public void genGroupBy(ASTPlainSelect plainSelect, Object data, boolean isTopSelect) {
		if (plainSelect.getGroupByColumnReferences() != null) {
			buffer.append(" GROUP BY ");

			for (int i = 0; i < plainSelect.getGroupByColumnReferences()
					.jjtGetNumChildren(); i++) {
				ASTColumnReference columnReference = (ASTColumnReference) plainSelect
						.getGroupByColumnReferences().jjtGetChild(i);
				columnReference.jjtAccept(this, data);
				if (i < plainSelect.getGroupByColumnReferences()
						.jjtGetNumChildren() - 1) {
					buffer.append(", ");
				}
			}
		}

		if (plainSelect.getHaving() != null) {
			buffer.append(" HAVING ");
			plainSelect.getHaving().jjtAccept(this, data);
		}
	}

	public Object visit(ASTHaving node, Object data) {
		return node.childrenAccept(this, data);
	}

	protected void genJoiner(ASTPlainSelect plainSelect, Object data) {
		if (plainSelect.getJoinsList() != null) {
			for (int i = 0; i < plainSelect.getJoinsList().jjtGetNumChildren(); i++) {

				ASTJoinerExpression join = (ASTJoinerExpression) plainSelect
						.getJoinsList().jjtGetChild(i);
				if (join.isOuter()) {
					buffer.append(" LEFT OUTER JOIN ");
				} else {
					buffer.append(" INNER JOIN ");
				}

				join.getFromItem().jjtAccept(this, data);
				if (join.getExpression() != null) {
					buffer.append(" ON ");
					topJoinExpression(data, join);
				}
			}
		}
	}

	protected void genJoinerNew(ASTJoinsList joinsList, Object data) {
		for (int i = 0; i < joinsList.jjtGetNumChildren(); i++) {
			ASTJoinerExpression join = (ASTJoinerExpression) joinsList
					.jjtGetChild(i);

			if (join.isOuter()) {
				buffer.append(" LEFT OUTER JOIN ");
			} else {
				buffer.append(" INNER JOIN ");
			}

			join.getFromItem().jjtAccept(this, data);
			if (join.getExpression() != null) {
				buffer.append(" ON ");

				topJoinExpression(data, join);
			}
		}
	}

	protected void topJoinExpression(Object data, ASTJoinerExpression join) {
		join.getExpression().jjtAccept(this, data);
	}

	public void genWhereSql(ASTPlainSelect plainSelect, Object data, boolean isTopSelect) {
		if (plainSelect.getWhere() != null) {
			buffer.append(" WHERE ");
			plainSelect.getWhere().jjtAccept(this, data);
		}
	}

	public void genFromSql(ASTPlainSelect plainSelect, Object data) {
		ASTFromItemsList fromItemsList = plainSelect.getFromItemsList();

		if (fromItemsList != null) {
			buffer.append("FROM ");

			for (int i = 0; i < fromItemsList.jjtGetNumChildren(); i += 2) {
				ASTFromItem fromItem = (ASTFromItem) fromItemsList
						.jjtGetChild(i);
				fromItem.jjtAccept(this, data);
				ASTJoinsList joinsList = (ASTJoinsList) fromItemsList
						.jjtGetChild(i + 1);
				genJoinerNew(joinsList, data);

				if (i < fromItemsList.jjtGetNumChildren() - 2) {
					buffer.append(", ");
				}
			}
		}
	}

	public void genSelectSql(ASTPlainSelect plainSelect, Object data, boolean isTopSelect) {
		if (count && isTopSelect) {
			buffer.append(" count(*) ");
		} else {
			ASTSelectItemsList selectItemsList = plainSelect.getSelectItemsList();

			for (int i = 0; i < selectItemsList.jjtGetNumChildren(); i++) {
				Node node = selectItemsList.jjtGetChild(i);
				node.jjtAccept(this, data);
				if (i < selectItemsList.jjtGetNumChildren() - 1) {
					buffer.append(", ");
				}
			}

			buffer.append(" ");
		}
	}

	public void genDistinctSql(ASTPlainSelect plainSelect, Object data) {
		ASTDistinct distinct = plainSelect.getDistinct();

		if (distinct != null) {
			buffer.append("DISTINCT ");
			if (distinct.jjtGetNumChildren() > 0) {
				buffer.append("ON (");
				ASTSelectItemsList list = (ASTSelectItemsList) distinct
						.jjtGetChild(0);
				for (int i = 0; i < list.jjtGetNumChildren(); i++) {
					ASTSelectExpressionItem selectItem = (ASTSelectExpressionItem) list
							.jjtGetChild(i);
					visit(selectItem, data);
					if (i < list.jjtGetNumChildren() - 1) {
						buffer.append(", ");
					}
				}

				buffer.append(") ");
			}
		}
	}

	public void genTopSql(ASTPlainSelect plainSelect) {
		ASTTop top = plainSelect.getTop();

		if (top != null)
			buffer.append("TOP "
					+ (top.isRowCountJdbcParameter() ? "?" : top.getRowCount()
							+ ""));
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public Object visit(ASTSelectExpressionItem node, Object data) {
		node.childrenAccept(this, data);

		if (node.getAlias() != null) {
			buffer.append(" AS " + node.getAlias());
		}

		return null;
	}

	public Object visit(ASTColumn node, Object data) {
		buffer.append(node.getWholeColumnName());
		return null;
	}

	public Object visit(ASTTable node, Object data) {
		buffer.append(node.getWholeTableName());
		return null;
	}

	public Object visit(ASTWhereClause node, Object data) {
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTExpression node, Object data) {
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTAnd node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Node c = (Node) node.jjtGetChild(i);
			boolean isor = false;
			if (c instanceof ASTOr) {
				isor = true;
			}

			if (isor) {
				buffer.append("(");
			}

			c.jjtAccept(this, data);

			if (isor) {
				buffer.append(")");
			}

			if (i < node.jjtGetNumChildren() - 1) {
				buffer.append(" and ");
			}
		}

		return null;
	}

	public Object visit(ASTOr node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Node c = (Node) node.jjtGetChild(i);

			c.jjtAccept(this, data);

			if (i < node.jjtGetNumChildren() - 1) {
				buffer.append(" or ");
			}
		}

		return null;
	}

	public Object visit(ASTRegularCondition node, Object data) {
		if (node.isNot()) {
			buffer.append(" NOT ");
		}

		Node left = node.jjtGetChild(0);
		left.jjtAccept(this, data);
		if (node.isLeftExternalLink()) {
			buffer.append("(+)");
		}

		buffer.append(" " + node.getOper() + " ");

		Node right = node.jjtGetChild(1);
		right.jjtAccept(this, data);
		if (node.isRightExternalLink()) {
			buffer.append("(+)");
		}

		return null;
	}

	public Object visit(ASTBetween between, Object data) {
		Node left = between.jjtGetChild(0);

		left.jjtAccept(this, data);
		if (between.isNot())
			buffer.append(" NOT");

		buffer.append(" BETWEEN ");
		Node start = between.jjtGetChild(1);
		start.jjtAccept(this, data);
		buffer.append(" AND ");
		Node end = between.jjtGetChild(2);
		end.jjtAccept(this, data);

		return null;
	}

	public Object visit(ASTInteger node, Object data) {
		buffer.append(node.jjtGetValue());
		return null;
	}

	public Object visit(ASTNull node, Object data) {
		buffer.append("null");
		return null;
	}

	public Object visit(ASTJdbcParameter node, Object data) {
		buffer.append("?");
		return null;
	}

	public Object visit(ASTDouble node, Object data) {
		buffer.append(node.jjtGetValue());
		return null;
	}

	public Object visit(ASTInverseExpression node, Object data) {
		buffer.append("-");
		node.childrenAccept(this, data);
		return null;
	}

	public Object visit(ASTString node, Object data) {
		buffer.append(node.jjtGetValue());
		return null;
	}

	public Object visit(ASTSubSelect subSelect, Object data) {
		buffer.append("(");
		subSelect.childrenAccept(this, data);
		buffer.append(")");
		return null;
	}

	public Object visit(ASTFromItem fromItem, Object data) {
		fromItem.childrenAccept(this, data);
		if (fromItem.getAlias() != null) {
			buffer.append(" " + fromItem.getAlias());
		}
		return null;
	}

	public Object visit(ASTInExpression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);

		if (node.isNotin())
			buffer.append(" NOT");
		buffer.append(" IN ");

		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	public Object visit(ASTLikeExpression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);

		if (node.isNotlike())
			buffer.append(" NOT");
		buffer.append(" like ");

		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	public Object visit(ASTIsNullExpression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		if (node.isNotnull()) {
			buffer.append(" IS NOT NULL");
		} else {
			buffer.append(" IS NULL");
		}

		return null;
	}

	public Object visit(ASTExistsExpression node, Object data) {
		if (node.isNotexists()) {
			buffer.append(" NOT EXISTS");
		} else {
			buffer.append(" EXISTS");
		}

		node.childrenAccept(this, data);

		return null;
	}

	public Object visit(ASTOrderByElement node, Object data) {
		node.childrenAccept(this, data);

		if (node.isAsc())
			buffer.append(" ASC");
		else
			buffer.append(" DESC");

		return null;
	}

	public Object visit(ASTAllTableColumns node, Object data) {
		return node.childrenAccept(this, data);
	}

	public Object visit(ASTAllTableCol node, Object data) {
		node.childrenAccept(this, data);
		buffer.append(".*");

		return null;
	}

	public Object visit(ASTAdd node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			if (i != 0) {
				buffer.append(node.getOpers().get(i));
			}
			Node c = (Node) node.jjtGetChild(i);
			c.jjtAccept(this, data);
		}

		return null;
	}

	public Object visit(ASTMult node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			if (i != 0) {
				buffer.append(node.getOpers().get(i));
			}

			Node c = (Node) node.jjtGetChild(i);
			boolean isadd = false;
			if (c instanceof ASTAdd) {
				isadd = true;
			}
			if (isadd) {
				buffer.append("(");
			}
			c.jjtAccept(this, data);
			if (isadd) {
				buffer.append(")");
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTCaseWhenExpression node, Object data) {
		buffer.append("CASE ");
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Node c = node.jjtGetChild(i);
			
			if (!(c instanceof ASTWhenThenSearchCondition)
					&& !(c instanceof ASTWhenThenValue)
					&& i == node.jjtGetNumChildren() - 1) {
				buffer.append(" ELSE ");
			}
			
			c.jjtAccept(this, data);
		}
		buffer.append(" END ");
		
		return null;
	}

	@Override
	public Object visit(ASTWhenThenSearchCondition node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			if (i == 0) {
				buffer.append(" WHEN ");
			} else if (i == 1) {
				buffer.append(" THEN ");
			} else {
				break;
			}
			
			Node c = node.jjtGetChild(i);
			c.jjtAccept(this, data);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTWhenThenValue node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			if (i == 0) {
				buffer.append(" WHEN ");
			} else if (i == 1) {
				buffer.append(" THEN ");
			} else {
				break;
			}
			
			Node c = node.jjtGetChild(i);
			c.jjtAccept(this, data);
		}
		
		return null;
	}
}
