export const API_BASE_URL = "http://localhost:8090";
// export const API_BASE_URL =  "http://ec2-65-1-4-68.ap-south-1.compute.amazonaws.com:8090";
export const ACCESS_TOKEN = "token";

export const CURRENT_USER = "user";

export const OAUTH2_REDIRECT_URI = "http://localhost:3000/oauth2/redirect";
// export const OAUTH2_REDIRECT_URI = "http://ec2-13-233-253-88.ap-south-1.compute.amazonaws.com/oauth2/redirect";

export const GOOGLE_AUTH_URL = API_BASE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;


