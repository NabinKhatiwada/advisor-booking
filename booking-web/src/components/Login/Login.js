import React from 'react';
import GoogleIcon from '@mui/icons-material/Google';
import './Login.css';
import googleLogo from "../../img/google-logo.png";
import { GOOGLE_AUTH_URL } from "../../constants";
export default function Login() {
  return (
    <div className="login-container">
      <div className="login-content">
        <h1 className="login-title">Login</h1>
        <GoogleLogin />
      </div>
    </div>
  )
}

export function GoogleLogin() {
  return (
    <div className="social-login">
      <a style={{border:'1px solid black'}} className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>



        <div style={{
          display: 'flex',
          alignItems: 'center',
          flexWrap: 'wrap',
        }}>
          <GoogleIcon />
          <span style={{ margin: '0rem 0 0 0.5rem' }} >Login With Google</span>
        </div>

      </a >
    </div >
  );
}