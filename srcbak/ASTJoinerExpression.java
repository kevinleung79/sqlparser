package selectParser.parser;

public class ASTJoinerExpression extends SimpleNode {
	private boolean outer = false;

	public ASTJoinerExpression(int id) {
		super(id);
	}

	public ASTJoinerExpression(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public boolean isOuter() {
		return outer;
	}

	public void setOuter(boolean outer) {
		this.outer = outer;
	}

	public ASTFromItem getFromItem() {
		Class c = ASTFromItem.class;
		return (ASTFromItem) getChildOfType(c);
	}
	public ASTExpression getExpression() {
		Class c = ASTExpression.class;
		return (ASTExpression) getChildOfType(c);
	}

}
