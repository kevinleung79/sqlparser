package selectParser.parser;

public class ASTInExpression extends SimpleNode {
	private boolean notin = false;
  public ASTInExpression(int id) {
    super(id);
  }

  public ASTInExpression(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public boolean isNotin() {
	return notin;
}

public void setNotin(boolean notin) {
	this.notin = notin;
}
}
