
package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {

	public static Logger	log;
	static {
		final Level level = Level.ALL;
		Log.log = Logger.getLogger(Log.class.getName());
		Log.log.setUseParentHandlers(false);

		Log.log.setLevel(level);
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new MyFormatter());
		handler.setLevel(level);
		Log.log.addHandler(handler);
	}
}

class MyFormatter extends Formatter {

	private static final DateFormat	df	= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");


	@Override
	public String format(final LogRecord record) {
		final StringBuilder builder = new StringBuilder();
		builder.append(MyFormatter.df.format(new Date(record.getMillis()))).append(" - ");
		builder.append("[").append(record.getSourceClassName()).append(".");
		builder.append(record.getSourceMethodName()).append("] - ");
		builder.append("[").append(record.getLevel()).append("] - ");
		builder.append(this.formatMessage(record));
		builder.append("\n");
		return builder.toString();
	}

	@Override
	public String getHead(final Handler h) {
		return super.getHead(h);
	}

	@Override
	public String getTail(final Handler h) {
		return super.getTail(h);
	}
}
