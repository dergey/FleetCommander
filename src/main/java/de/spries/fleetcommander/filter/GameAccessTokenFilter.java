package de.spries.fleetcommander.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;

import java.util.regex.Pattern;

public class GameAccessTokenFilter /* implements ContainerRequestFilter */ {

	private static final Logger LOGGER = LogManager.getLogger(GameAccessTokenFilter.class.getName());

	public static final String AUTH_HEADER = "Authorization";
	public static final String AUTH_TOKEN_PREFIX = "Bearer ";
	private static final Pattern PROTECTED_PATHS = Pattern.compile("^games/(?<gameId>\\d+)");

//	@Override
//	public void filter(ContainerRequestContext requestCtx) throws IOException {
//		String path = requestCtx.getUriInfo().getPath();
//
//		Matcher protectedPathMatcher = PROTECTED_PATHS.matcher(path);
//		if (protectedPathMatcher.find()) {
//
//			int gameId = Integer.parseInt(protectedPathMatcher.group("gameId"));
//
//			int playerId = 0;
//			String token = null;
//			try {
//				playerId = extractPlayerIdFromContext(requestCtx);
//				token = extractAuthTokenFromContext(requestCtx);
//			} catch (Exception e) {
//				LOGGER.warn("Invalid request headers (playerId/token couldn't be extracted)", e);
//				requestCtx.abortWith(Response.status(Response.Status.BAD_REQUEST)
//						.entity(new RestError("Required header 'Authorization' was missing or malformed."
//								+ " Expexted value: 'Bearer <playerId>:<authToken>'")).build());
//			}
//
//			GamePlayer gamePlayer = GamePlayer.forIds(gameId, playerId);
//			if (!GameAuthenticatorService.INSTANCE.isAuthTokenValid(gamePlayer, token)) {
//				LOGGER.warn("{}: Unauthorized access with token {}", gamePlayer, token);
//				requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
//						.entity(new RestError("'Authorization' header was invalid for game " + gameId)).build());
//			}
//		}
//	}

//	private static int extractPlayerIdFromContext(ContainerRequestContext requestCtx) {
//		String playerToken = extractAuthFromContext(requestCtx);
//		String[] playerAndToken = playerToken.split(":");
//		return Integer.parseInt(playerAndToken[0]);
//	}

	public static int extractPlayerIdFromHeaders(HttpHeaders headers) {
		String playerToken = extractAuthFromHeaders(headers);
		String[] playerAndToken = playerToken.split(":");
		return Integer.parseInt(playerAndToken[0]);
	}

	public static int extractPlayerIdFromHeadersValue(String value) {
		String playerToken = extractTokenFromHeaderValue(value);
		String[] playerAndToken = playerToken.split(":");
		return Integer.parseInt(playerAndToken[0]);
	}

//	private static String extractAuthTokenFromContext(ContainerRequestContext requestCtx) {
//		String playerToken = extractAuthFromContext(requestCtx);
//		String[] playerAndToken = playerToken.split(":");
//		return playerAndToken[1];
//	}
//
//	private static String extractAuthFromContext(ContainerRequestContext requestCtx) {
//		String token = requestCtx.getHeaderString(AUTH_HEADER);
//		return extractTokenFromHeaderValue(token);
//	}

	private static String extractAuthFromHeaders(HttpHeaders headers) {
		String token = headers.getFirst(AUTH_HEADER);
		return extractTokenFromHeaderValue(token);
	}

	private static String extractTokenFromHeaderValue(String token) {
		if (token != null && token.startsWith(AUTH_TOKEN_PREFIX)) {
			return token.substring(AUTH_TOKEN_PREFIX.length());
		}
		return null;
	}

}
