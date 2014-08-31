package selectParser.visitor;

import java.util.List;

public interface SqlConditionExtractor {
	void doExtract(StringBuffer buffer, List<Object> parameters);
}
