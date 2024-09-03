package com.spectra.asr.constants;

/**
 * Holds the application specific constants.
 */
public final class ApplicationConstants {

	/**
	 * Default Constructor.
	 */
	private ApplicationConstants() {

	}

	public static final String URL_PKG_PREFIXES = "URL_PKG_PREFIXES";
	public static final String SECURITY_PRINCIPAL = "SECURITY_PRINCIPAL";
	public static final String SECURITY_CREDENTIALS = "SECURITY_CREDENTIALS";
	public static final String JBOSS_NAMING_CLIENT_EJB_CONTEXT = "jboss.naming.client.ejb.context";
	
	/**
	 * Constant for initial context key in the ApplicationProperties.
	 */
	public static final String INITIAL_CONTEXT_FACTORY_KEY = "INITIAL_CONTEXT_FACTORY";

	/**
	 *Constant for naming provider url key in the ApplicationProperties.
	 */
	public static final String NAMING_PROVIDER_URL_KEY = "NAMING_PROVIDER_URL";

	/**
	 *Constant for data source key in the ApplicationProperties.
	 */
	public static final String DATASOURCE_KEY = "DataSource";

	/**
	 *Constant for storing ErrorMessage.
	 */
	public static final String ERROR_MESSAGE = "ErrorMessage";

	/**
	 *Constant for storing the pattern of log file. The following pattern
	 *will create a file ScorpionApp0.log in the home directory of weblogic user
	 *(which is running the application). %u stands for a unique number to
	 *differentiate the generated file from previous files.
	 */
	public static final String APP_LOG_FILE_PATTERN = "APP_LOG_FILE_PATTERN";

	/**
	 *Constant for storing the pattern of performance log file. Same as above.
	 */
	public static final String PERF_LOG_FILE_PATTERN = "PERF_LOG_FILE_PATTERN";

	/**
	 *Limit after which the log file is rotated.
	 */
	public static final int LOG_FILE_LIMIT = 5242880;

	/**
	 *Count upto which rotated log files will be maintained.
	 *Beyond this limit the files will be deleted.
	 */
	public static final int LOG_FILE_COUNT = 50;

	/**
	 *Constant indicating whether an append with running sequence
	 *is required.
	 */
	public static final boolean LOG_FILE_APPEND = Boolean.TRUE;

	/**
	 *Constant to obtain the encoding type used in the filter.
	 */
	public static final String ENCODING = "encoding";

	/**
	 * Regular expression constant representing the parameter string 1, etc.
	 */
	public static final String REGEX_PARAMETER = "[0-9]*";

	/**
	 * Regular expression constant representing the parameter 
	 * string with only alphabets.
	 */
	public static final String REGEX_PARAMETER_ALPHABETS = "[A-Z][a-z]*";

	/**
	 * Regular expression constant representing the parameter 
	 * string with only alphabets and comma.
	 */
	public static final String REGEX_ALPHABETS = "^([a-z-A-Z]| )*$";


	/**
	 * Constant to denote logger name for performance logging
	 */
	public static final String PERFORMANCE_LOGGER = "PERFORMANCE_LOGGER";

	/**
	 * Constant to denote logger level for application logging
	 */
	public static final String APP_LOG_LEVEL = "APP_LOG_LEVEL";

	/**
	 * Constant to denote logger level for performance logging
	 */
	public static final String PERF_LOG_LEVEL = "PERF_LOG_LEVEL";

	/**
	 *Constant for data source key in the ApplicationProperties.
	 */
	public static final String DWDATASOURCE_KEY = "DWDataSource";

    /**
     * Constant to hold the variable.
     */
    public static final String RESOURCE_I18N = "resources.I18NResource";
    
    public static final String CHAR_ENCODING = "UTF-8";
    
    // date pattern
    public static final String DP_DATE_ONLY = "MM/dd/yyyy";
    public static final String DP_DATETIME_ONLY = "MM/dd/yyyy HH:mm:ss";
}
