package selectParser.visitor;

import selectParser.parser.ASTAList;
import selectParser.parser.ASTAdd;
import selectParser.parser.ASTAllCol;
import selectParser.parser.ASTAllComparisonExpression;
import selectParser.parser.ASTAllTableCol;
import selectParser.parser.ASTAllTableColumns;
import selectParser.parser.ASTAnd;
import selectParser.parser.ASTAnyComparisonExpression;
import selectParser.parser.ASTBetween;
import selectParser.parser.ASTCaseWhenExpression;
import selectParser.parser.ASTColumn;
import selectParser.parser.ASTColumnIndex;
import selectParser.parser.ASTColumnReference;
import selectParser.parser.ASTColumnsNamesList;
import selectParser.parser.ASTDistinct;
import selectParser.parser.ASTDouble;
import selectParser.parser.ASTExistsExpression;
import selectParser.parser.ASTExpression;
import selectParser.parser.ASTFromItem;
import selectParser.parser.ASTFromItemsList;
import selectParser.parser.ASTFunction;
import selectParser.parser.ASTGroupByColumnReferences;
import selectParser.parser.ASTHaving;
import selectParser.parser.ASTInExpression;
import selectParser.parser.ASTInteger;
import selectParser.parser.ASTInverseExpression;
import selectParser.parser.ASTIsNullExpression;
import selectParser.parser.ASTJdbcParameter;
import selectParser.parser.ASTJoinerExpression;
import selectParser.parser.ASTJoinsList;
import selectParser.parser.ASTLikeExpression;
import selectParser.parser.ASTMult;
import selectParser.parser.ASTNull;
import selectParser.parser.ASTOr;
import selectParser.parser.ASTOrderByElement;
import selectParser.parser.ASTOrderByElements;
import selectParser.parser.ASTPlainSelect;
import selectParser.parser.ASTRegularCondition;
import selectParser.parser.ASTSQLExpressionList;
import selectParser.parser.ASTSelect;
import selectParser.parser.ASTSelectExpressionItem;
import selectParser.parser.ASTSelectItemsList;
import selectParser.parser.ASTSimpleExpressionList;
import selectParser.parser.ASTString;
import selectParser.parser.ASTSubSelect;
import selectParser.parser.ASTTable;
import selectParser.parser.ASTTableWithAlias;
import selectParser.parser.ASTTop;
import selectParser.parser.ASTUDFFunc;
import selectParser.parser.ASTUnion;
import selectParser.parser.ASTWhenThenSearchCondition;
import selectParser.parser.ASTWhenThenValue;
import selectParser.parser.ASTWhereClause;
import selectParser.parser.SelectParserVisitor;
import selectParser.parser.SimpleNode;

public class DefaultParserVisitor implements SelectParserVisitor {

	public Object visit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTColumn node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTTableWithAlias node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTTable node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSelect node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTPlainSelect node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSelectItemsList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAllCol node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAllTableCol node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSelectExpressionItem node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAllTableColumns node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTFromItemsList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTJoinsList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTJoinerExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTWhereClause node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTGroupByColumnReferences node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTHaving node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTOrderByElements node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTOrderByElement node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTTop node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTColumnReference node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTColumnIndex node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTOr node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAnd node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTRegularCondition node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTInExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTBetween node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTLikeExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTIsNullExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTExistsExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSQLExpressionList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSimpleExpressionList node, Object data) {
		return null;

	}

	public Object visit(ASTAllComparisonExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAnyComparisonExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAdd node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTMult node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTCaseWhenExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTWhenThenSearchCondition node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTWhenThenValue node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTSubSelect node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTAList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTColumnsNamesList node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTDistinct node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTFromItem node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTInteger node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTNull node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTJdbcParameter node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTDouble node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTInverseExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTString node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTFunction node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTUDFFunc node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(ASTUnion node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

}
