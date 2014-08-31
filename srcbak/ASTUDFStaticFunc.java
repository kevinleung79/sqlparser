package selectParser.parser;

import java.util.HashMap;
import java.util.Map;

public class ASTUDFStaticFunc extends SimpleNode {
	private String name = null;
	private Map parameters = new HashMap();

	public ASTUDFStaticFunc(int id) {
		super(id);
	}

	public ASTUDFStaticFunc(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getStaticParameters() {
		return parameters;
	}

	public void setStaticParameters(Map parameters) {
		this.parameters = parameters;
	}
}
