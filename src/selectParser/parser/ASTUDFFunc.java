package selectParser.parser;

import java.util.HashMap;
import java.util.Map;

public class ASTUDFFunc extends SimpleNode {
	private String name = null;
	private Map parameters = new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ASTUDFFunc(int id) {
		super(id);
	}

	public ASTUDFFunc(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
}
