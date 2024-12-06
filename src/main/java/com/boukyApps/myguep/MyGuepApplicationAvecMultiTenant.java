//package com.boukyApps.myguep;
//
//import com.boukyApps.myguep.model.User;
//import com.boukyApps.myguep.service.DataSourceService;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.function.HandlerFunction;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerRequest;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.springframework.web.servlet.function.RouterFunctions.route;
//
//@SpringBootApplication
//public class MyGuepApplicationAvecMultiTenant {
//
//	public static void main(String[] args) {
//		SpringApplication.run(MyGuepApplication.class, args);
//	}
//
//	@Bean
//	RouterFunction<ServerResponse> routes (JdbcTemplate template){
//		return route()
//				.GET("/customer", new HandlerFunction<ServerResponse>() {
//					@Override
//					public ServerResponse handle(ServerRequest request) throws Exception {
//						var results = template.query("select * from customer",
//								new RowMapper<User>() {
//									@Override
//									public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//										return new User(rs.getLong("id"), rs.getString("name"));
//									}
//								});
//						return ServerResponse.ok().body(results);
//					}
//				})
//				.build();
//	}
//}
//
//
//
//@Component
//class DataSourceConfiguration{
//
//	private final DataSourceService dataSourceService;
//
//	public DataSourceConfiguration(DataSourceService dataSourceService) {
//		this.dataSourceService = dataSourceService;
//	}
//
//	@Bean
//	@Primary
//	public DataSource multitenantDataSource() {
//		var mds = new MultitenantDataSource(dataSourceService);
//		return mds;
//	}
//
////	@Bean
////	@Primary
////	DataSource multitenantDataSource(Map<String, DataSource> dataSources){
////		var prefix = "ds";
////		var map = dataSources
////				.entrySet()
////				.stream()
////				.filter(e -> e.getKey().startsWith(prefix))
////				.collect(Collectors.toMap(
////						e -> (Object) Integer.parseInt(e.getKey().substring(prefix.length())),
////						e -> (Object) e.getValue()));
////		map.forEach((tenantId, ds) -> {
////			var initializer = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"),
////					new ClassPathResource(prefix + tenantId + "-data.sql"));
////			initializer.execute((DataSource) ds);
////			System.out.println("initialized " + tenantId);
////		});
////		var mds= new MultitenantDataSource();
////		mds.setTargetDataSources(map);
////		return mds;
////	}
////
////	@Bean
////	DataSource ds01 (){
////		return dataSource(54301);
////	}
////
////	@Bean
////	DataSource ds02 (){
////		return dataSource(54302);
////	}
////
////	private static DataSource dataSource(int port){
////		var dsp = new DataSourceProperties();
////		dsp.setPassword("pwd");
////		dsp.setUsername("user");
////		dsp.setUrl("jdbc:postgresql://localhost:"+port+"/user");
////		return dsp.initializeDataSourceBuilder()
////				.type(HikariDataSource.class)
////				.build();
////	}
//}
//
//@Configuration
//class SecurityConfiguration{
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.httpBasic(Customizer.withDefaults())
//				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//				.csrf(x -> x.disable());
//		return http.build();
//	}
//
//	@Bean
//	UserDetailsService userDetailsService(){
//		var rob  = createUser("rwinch", 1);
//		var josh  = createUser("jlong", 2);
//		var users = Stream.of(josh, rob)
//				.collect(Collectors.toMap(org.springframework.security.core.userdetails.User::getUsername, u -> u));
//		return username -> {
//			var user = users.getOrDefault(username, null);
//			if (user==null){
//				throw new UsernameNotFoundException("couldn't find "+username+" user");
//			}
//			return user;
//		};
//	}
//
//	private static org.springframework.security.core.userdetails.User createUser ( String username, Integer tenantId){
//		return new MultitenantUser(username,
//				"pw", true, true, true,
//				true, tenantId);
//	}
//}
//
//class MultitenantUser extends org.springframework.security.core.userdetails.User {
//	private final Integer tenantId;
//
//	public MultitenantUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Integer tenantId) {
//		super(username, PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
//				List.of(new SimpleGrantedAuthority("USER")));
//		this.tenantId = tenantId;
//	}
//
//	public Integer getTenantId() {
//		return tenantId;
//	}
//}
//
//class MultitenantDataSource extends AbstractRoutingDataSource {
//
//	private final AtomicBoolean initialized = new AtomicBoolean();
//	private final DataSourceService dataSourceService;
//
//	public MultitenantDataSource(DataSourceService dataSourceService) {
//		this.dataSourceService = dataSourceService;
//	}
//
//	@Override
//	protected DataSource determineTargetDataSource() {
//		if (this.initialized.compareAndSet(false, true)) {
//			this.afterPropertiesSet();
//		}
//		return super.determineTargetDataSource();
//	}
//
//	@Override
//	protected Object determineCurrentLookupKey() {
//		var authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null && authentication.getPrincipal() instanceof MultitenantUser user) {
//			var tenantId = user.getTenantId();
//			return tenantId;
//		}
//		return null;
//	}
//
//	@Override
//	public void afterPropertiesSet() {
//		var tenantIds = getAllTenantIds(); // Method to get all tenant IDs (possibly from a database)
//		var dataSources = tenantIds.stream()
//				.collect(Collectors.toMap(
//						tenantId -> (Object) tenantId,
//						tenantId -> (Object) dataSourceService.getDataSource(tenantId)
//				));
//		setTargetDataSources(dataSources);
//		super.afterPropertiesSet();
//	}
//
//	private List<Integer> getAllTenantIds() {
//		// Logic to get all tenant IDs from your storage (e.g., a database or configuration)
//		return List.of(1, 2); // Example tenant IDs
//	}
////	private final AtomicBoolean initializied = new AtomicBoolean();
////
////	@Override
////	protected DataSource determineTargetDataSource() {
////		if(this.initializied.compareAndSet(false, true)){
////			this.afterPropertiesSet();
////		}
////		return super.determineTargetDataSource();
////	}
////
////	@Override
////	protected Object determineCurrentLookupKey() {
////		var authentication = SecurityContextHolder.getContext().getAuthentication();
////		if (authentication != null && authentication.getPrincipal() instanceof MultitenantUser user ){
////			var tenantId = user.getTenantId();
////			System.out.println("The tenantId is "+tenantId);
////			return tenantId;
////		}
////		return null;
////	}
//}

