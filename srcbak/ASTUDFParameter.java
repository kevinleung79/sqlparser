package selectParser.parser;

public class ASTUDFParameter extends SimpleNode {
	private String name = null;

	private String value = null;

	public ASTUDFParameter(int id) {
		super(id);
	}

	public ASTUDFParameter(SelectParser p, int id) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
