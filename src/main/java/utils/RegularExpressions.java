package utils;


/**
 * Interface contains patterns needs to parse HTML code
 *
 * @author Verbitsky Sergey
 */

public interface RegularExpressions {
    String PATTERN_A_TAG_PART = "(?i)<a(.+?)href(\\s)*=(\\s)*\"(.*://)*(.*?)(?=\")(.*?)(</a>)";
    String PATTERN_BASE_DOMAIN_PART = ".*?(://)(.*?)(/.*)";
    String PATTERN_INVISIBLE_LINK_PROPERTY = "display(\\s)*?(:)(\\s)*?none";
    String PATTERN_TITLE_TAG = "(<title>.*?</title>)";
    String PATTERN_SCRIPT_TAG = "(<script.*?</script>)";
    String PATTERN_SCRIPT_TAG2 = "(<script>.*?</script>)";
    String PATTERN_TAG = "(<.*?>)";


    String HTTP_PROTOCOL_DELIMITER = "://";
    String HTTP_BASE_PROTOCOL = "https";
    String HTTP_BASE_PROTOCOL_DOTS = "https:";

}
