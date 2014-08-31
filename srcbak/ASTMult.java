package selectParser.parser;

import java.util.ArrayList;
import java.util.List;

public class ASTMult extends SimpleNode {
	private List opers = new ArrayList();
  public ASTMult(int id) {
    super(id);
  }

  public ASTMult(SelectParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SelectParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public List getOpers() {
	return opers;
}

public void setOpers(List opers) {
	this.opers = opers;
}
}
