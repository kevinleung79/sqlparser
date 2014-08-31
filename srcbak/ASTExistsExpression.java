package selectParser.parser;

public class ASTExistsExpression extends SimpleNode {
	private boolean notexists = false;
  public ASTExistsExpression(int id) {
    super(id);
  }

  public ASTExistsExpression(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public boolean isNotexists() {
	return notexists;
}

public void setNotexists(boolean notexists) {
	this.notexists = notexists;
}
}
