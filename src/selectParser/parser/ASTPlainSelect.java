package selectParser.parser;

public class ASTPlainSelect extends SimpleNode {
	private long rowCount;

	private boolean rowCountJdbcParameter = false;

	public ASTPlainSelect(int id) {
		super(id);
	}

	public ASTPlainSelect(SelectParser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. * */
	public Object jjtAccept(SelectParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public ASTTop getTop() {
		Class c = ASTTop.class;

		return (ASTTop) getChildOfType(c);
	}
	public ASTDistinct getDistinct(){
		Class c = ASTDistinct.class;
		return (ASTDistinct) getChildOfType(c);
	}
	public ASTSelectItemsList getSelectItemsList(){
		Class c = ASTSelectItemsList.class;
		return (ASTSelectItemsList) getChildOfType(c);
	}
	public ASTFromItemsList getFromItemsList(){
		Class c = ASTFromItemsList.class;
		return (ASTFromItemsList) getChildOfType(c);
	}
	public ASTWhereClause getWhere(){
		Class c = ASTWhereClause.class;
		return (ASTWhereClause) getChildOfType(c);
	}
	public ASTGroupByColumnReferences getGroupBy(){
		Class c = ASTGroupByColumnReferences.class;
		return (ASTGroupByColumnReferences) getChildOfType(c);
	}
	public boolean isCount(){
		return false;
	}
	public ASTOrderByElements getOrderBy(){
		Class c = ASTOrderByElements.class;
		return (ASTOrderByElements) getChildOfType(c);
	}
	public ASTJoinsList getJoinsList(){
		Class c = ASTJoinsList.class;
		return (ASTJoinsList) getChildOfType(c);
	}
	public ASTGroupByColumnReferences getGroupByColumnReferences(){
		Class c = ASTGroupByColumnReferences.class;
		return (ASTGroupByColumnReferences) getChildOfType(c);
	}
	
	
	public ASTHaving getHaving(){
		Class c = ASTHaving.class;
		return (ASTHaving) getChildOfType(c);
	}

}

