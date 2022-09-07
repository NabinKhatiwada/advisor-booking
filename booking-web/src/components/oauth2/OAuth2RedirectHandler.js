import React, { useState, useEffect } from 'react';
import { ACCESS_TOKEN } from '../../constants';
import { Navigate } from 'react-router-dom';
import { useSearchParams } from "react-router-dom";

const  OAuth2RedirectHandler = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [token,setToken] = useState(null);
    const [loading,setLoading] = useState(true);
    useEffect(()=>{
       let token = searchParams.get("token");
       console.log("TOKEN = ",token);
       setToken(token);
       setLoading(false);
    },[]);
        
        if(loading){
            return <>Loading....</>;
        }

        if (token) {
            window.localStorage.setItem(ACCESS_TOKEN, token)
            /* As soon as I get token, store it in local */
            
            return <Navigate to={{
                pathname: "/dashboard"
            }} />;
        } else {
            return <Navigate to={{
                pathname: "/login"
            }} />;
        }
    
}

export default OAuth2RedirectHandler;