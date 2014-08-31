package selectParser.parser;

public class ASTBetween extends SimpleNode {
	private boolean not = false;

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public ASTBetween(int id) {
		super(id);
	}

	public ASTBetween(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
