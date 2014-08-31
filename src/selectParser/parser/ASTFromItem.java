package selectParser.parser;

public class ASTFromItem extends SimpleNode {
	private String alias = null;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ASTFromItem(int id) {
		super(id);
	}

	public ASTFromItem(SelectParser p, int id) {
		super(p, id);
	}
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
