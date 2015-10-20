package ldaptest;


import static org.junit.Assert.assertTrue;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.apache.directory.server.integ.ServerIntegrationUtils;
import org.apache.directory.server.ldap.LdapServer;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
@RunWith(FrameworkRunner.class)
@CreateLdapServer(transports = {@CreateTransport(protocol = "LDAP")})
public class SearchTests extends AbstractLdapTestUnit {

    public static LdapServer ldapServer;

    @Test
    public void testSearchAllAttrs() throws Exception {
        LdapContext ctx = (LdapContext) ServerIntegrationUtils.getWiredContext(ldapServer, null).lookup("ou=system");

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        controls.setReturningAttributes(new String[]{"+", "*"});

        NamingEnumeration<SearchResult> res = ctx.search("", "(ObjectClass=*)", controls);

        assertTrue(res.hasMore());

        while (res.hasMoreElements()) {
            SearchResult result = (SearchResult) res.next();

            System.out.println(result.getName());
        }
    }
}