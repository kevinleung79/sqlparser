package selectParser.parser;

public class ASTSelectExpressionItem extends SimpleNode {
	public String toString() {
		return "ASTSelectExpressionItem:alias="+alias;
	}

	private String alias = null;

	public ASTSelectExpressionItem(int id) {
		super(id);
	}

	public ASTSelectExpressionItem(SelectParser p, int id) {
		super(p, id);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
