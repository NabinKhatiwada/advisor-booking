import React from 'react';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import Login from '../Login/Login';
import OAuth2RedirectHandler from '../oauth2/OAuth2RedirectHandler';
import { PrivateRoute } from "./PrivateRoute";
import Advisor from '../Dashboard/Advisor/Advisor';
import Client from '../Dashboard/Client/Client';
import NoMatch from '../../utils/NoMatch'
import Layout from '../Dashboard/Layout';
import { ToastContainer } from 'react-toastify';
import MyBooking from '../Dashboard/Client/MyBooking';
import Chat from '../Dashboard/Chat/Chat';
function App() {
  // const { token, setToken } = useToken();

  // if(!token) {
  //   return <Login setToken={setToken} />
  // }

  return (
    <div className="wrapper">
      <ToastContainer />
      <Routes>

        <Route
          path="/oauth2/redirect/"
          element={<OAuth2RedirectHandler />}
        ></Route>
        <Route exact path="/" element={<Login />} />
        <Route exact path="/login" element={<Login />} />

        <Route
          path="/dashboard"
          element={<PrivateRoute component={Layout} />}
        >
          <Route path="/dashboard/advisor" element={<Advisor />} />
          <Route path="/dashboard/client" element={<Client />} />
          <Route path="/dashboard/client-booking" element={<MyBooking />} />
          <Route path="/dashboard/chat" element={<Chat />} />
        </Route>

        <Route path="*" element={<NoMatch />} />
      </Routes>

    </div>
  );
}
export default App;