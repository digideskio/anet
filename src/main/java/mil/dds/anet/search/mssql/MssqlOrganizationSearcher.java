package mil.dds.anet.search.mssql;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.skife.jdbi.v2.Handle;

import jersey.repackaged.com.google.common.base.Joiner;
import mil.dds.anet.beans.Organization;
import mil.dds.anet.beans.search.OrganizationSearchQuery;
import mil.dds.anet.database.OrganizationDao;
import mil.dds.anet.database.mappers.OrganizationMapper;
import mil.dds.anet.search.IOrganizationSearcher;
import mil.dds.anet.utils.DaoUtils;

public class MssqlOrganizationSearcher implements IOrganizationSearcher {

	@Override
	public List<Organization> runSearch(OrganizationSearchQuery query, Handle dbHandle) {
		StringBuilder sql = new StringBuilder("SELECT " + OrganizationDao.ORGANIZATION_FIELDS
				+ " FROM organizations WHERE organizations.id IN (SELECT organizations.id FROM organizations ");
		Map<String,Object> sqlArgs = new HashMap<String,Object>();
		
		sql.append(" WHERE ");
		List<String> whereClauses = new LinkedList<String>();
		
		String text = query.getText();
		if (text != null && text.trim().length() > 0) {
			text = "\"" + text + "*\"";
			whereClauses.add("CONTAINS(name, :text)");
			sqlArgs.put("text", text);
		}
		
		if (query.getType() != null) { 
			whereClauses.add(" organizations.type = :type ");
			sqlArgs.put("type", DaoUtils.getEnumId(query.getType()));
		}
		
		sql.append(Joiner.on(" AND ").join(whereClauses));
		
		sql.append(")");
		
		return dbHandle.createQuery(sql.toString())
			.bindFromMap(sqlArgs)
			.map(new OrganizationMapper())
			.list();
	}
	
}