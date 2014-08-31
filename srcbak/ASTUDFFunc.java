package selectParser.parser;

public class ASTUDFFunc extends SimpleNode {
	private String name = null;

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
}
