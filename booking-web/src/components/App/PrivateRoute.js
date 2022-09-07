import React from "react";
import {  Navigate } from "react-router-dom";
import { isLoggedIn } from "../oauth2/authentication";

export const PrivateRoute = ({ component: RouteComponent }) => {
  var isAuthenticated = isLoggedIn();
  return isAuthenticated ? <RouteComponent /> : <Navigate to="/login" />;
};