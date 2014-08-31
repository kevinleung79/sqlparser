package selectParser.parser;

public class ASTLikeExpression extends SimpleNode {
	private boolean notlike = false;
  public boolean isNotlike() {
		return notlike;
	}

	public void setNotlike(boolean notlike) {
		this.notlike = notlike;
	}

public ASTLikeExpression(int id) {
    super(id);
  }

  public ASTLikeExpression(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
