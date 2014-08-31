package selectParser.parser;

public class ASTIsNullExpression extends SimpleNode {
	private boolean notnull=false;
  public ASTIsNullExpression(int id) {
    super(id);
  }

  public ASTIsNullExpression(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public boolean isNotnull() {
	return notnull;
}

public void setNotnull(boolean notnull) {
	this.notnull = notnull;
}
}
