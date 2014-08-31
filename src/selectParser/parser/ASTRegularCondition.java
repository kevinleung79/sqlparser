package selectParser.parser;

public class ASTRegularCondition extends SimpleNode {
	private String oper = null;
	private boolean leftExternalLink = false;
	private boolean rightExternalLink = false;
	private boolean isNot = false;

	public String toString() {
		String addStr = "";
		if (leftExternalLink) {
			addStr += "LeftExternalLink";
		}
		if (rightExternalLink) {
			if(leftExternalLink){
				addStr += ",";
			}
			addStr += "RightExternalLink";
		}
		return "ASTRegularCondition:oper=" + oper +" "+ addStr;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public ASTRegularCondition(int id) {
		super(id);
	}

	public ASTRegularCondition(SelectParser p, int id) {
		super(p, id);
	}

	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public boolean isLeftExternalLink() {
		return leftExternalLink;
	}

	public void setLeftExternalLink(boolean leftExternalLink) {
		this.leftExternalLink = leftExternalLink;
	}

	public boolean isRightExternalLink() {
		return rightExternalLink;
	}

	public void setRightExternalLink(boolean rightExternalLink) {
		this.rightExternalLink = rightExternalLink;
	}

	public boolean isNot() {
		return isNot;
	}

	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}
}
