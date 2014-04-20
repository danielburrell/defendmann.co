package globals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Comparator;

import models.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.annotations.Beta;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import controllers.DataFetcher;
import dao.TourDao;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.db.DB;
import play.libs.Akka;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Result;
import scala.concurrent.duration.Duration;

public class Global extends GlobalSettings {

	private static final Injector INJECTOR = createInjector();

	@Inject
	private DataFetcher runnable;

	@Override
	public void onStart(Application arg0) {
		Logger.info("Scheduling Cache Refresher");
		INJECTOR.injectMembers(this);
		Akka.system()
				.scheduler()
				.schedule(Duration.create(0, TimeUnit.MILLISECONDS),
						Duration.create(10, TimeUnit.SECONDS), runnable,
						Akka.system().dispatcher()
				);
	}

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
			throws Exception {
		return INJECTOR.getInstance(controllerClass);
	}

	private static Injector createInjector() {
		return Guice.createInjector();
	}

}