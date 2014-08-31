package selectParser.parser;

public class ASTUnion extends SimpleNode {
	private boolean isAll;

	public ASTUnion(int id) {
		super(id);
	}

	public ASTUnion(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. **/
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public boolean isAll() {
		return isAll;
	}

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

}
