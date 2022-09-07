// import React, { useEffect,useState } from 'react';
// import { getProfile } from '../../services/api';
// const Dashboard = () => {
//   const [profile,setProfile] = useState(null);

//   const getMe = async(token) => {
//     let user = await getProfile(token);
//     setProfile(user);
//   }

//   useEffect(() => {
//     let token = localStorage.getItem('token');
//     getMe(token);

//   }, []);

//   return (

//     <h2>Dashboard </h2>
//   );
// }
// export default Dashboard;
