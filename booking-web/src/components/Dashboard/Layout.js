import React, { useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import DashboardIcon from '@mui/icons-material/Dashboard';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import PeopleAltOutlinedIcon from '@mui/icons-material/PeopleAltOutlined';
import { makeStyles } from '@mui/styles';
import { Outlet } from 'react-router-dom';

import { useNavigate, useLocation } from 'react-router-dom';
import { format } from 'date-fns';
import GavelIcon from '@mui/icons-material/Gavel';
import LogoutIcon from '@mui/icons-material/Logout';
import { ToastContainer } from 'react-toastify';

import { getProfile } from '../../services/api';
import BookOnlineIcon from '@mui/icons-material/BookOnline';
import { Chat } from '@mui/icons-material';
const drawerWidth = 250;

const useStyles = makeStyles((theme) => {
  return {
    page: {
      background: '#ffffff',
      width: '100%',
      padding: theme.spacing(3),
    },
    root: {
      display: 'flex',
    },
    drawer: {
      width: drawerWidth,
    },
    drawerPaper: {
      width: drawerWidth,
    },
    active: {
      background: '#f4f4f4'
    },
    title: {
      padding: '0.5rem 0rem',
    },
    appBar: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth,
    },
    date: {
      flexGrow: 1
    },
    toolbar: theme.mixins.toolbar,
    avatar: {
      marginLeft: theme.spacing(1)
    }
  }
})

const Layout = ({ children }) => {
  const classes = useStyles()
  const navigate = useNavigate()
  const location = useLocation();

  const [profile, setProfile] = useState(null);
  const getMe = async (token) => {
    let user = await getProfile(token);
    setProfile(user);
  }

  useEffect(() => {
    let token = localStorage.getItem('token');
    getMe(token);

  }, []);


  const clientMenus = [
    {
      text: 'Client Section',
      path: '/dashboard/client',
      icon: <PeopleAltOutlinedIcon color='secondary' />
    },
    {
      text: 'My Bookings',
      path: '/dashboard/client-booking',
      icon: <BookOnlineIcon color='secondary' />
    },
    {
      text: 'Chat',
      path: '/dashboard/chat',
      icon: <Chat color='secondary' />
    }
  ];

  const advisorMenus = [
    {
      text: 'Advisor Section',
      path: '/dashboard/advisor',
      icon: <GavelIcon color='secondary' />
    },
    {
      text: 'Chat',
      path: '/dashboard/chat',
      icon: <Chat color='secondary' />
    }
  ];

  const handleLogout = () => {
    localStorage.clear();
    navigate("../", { replace: true });
  }

  if (profile === null) {
    return <>Loading...</>
  }

  return (
    <div className={classes.root}>
      {/* app bar */}
      <AppBar
        position="fixed"
        className={classes.appBar}
        elevation={9}
        color="primary"
      >
        <Toolbar>
          <Typography className={classes.date}>
            Today is the {format(new Date(), 'do MMMM Y')}
          </Typography>
          <div style={{ display: 'flex', margin: '0 0 0 auto' }}>
            <ListItem style={{ cursor: 'pointer' }}  >
            <Avatar src={profile.imageUrl} /> {profile.name}
              <LogoutIcon button onClick={() => handleLogout()} style={{ margin: '0 0 0 1rem' }} />
            </ListItem>
          </div>
        </Toolbar>
      </AppBar>

      {/* side drawer */}
      <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{ paper: classes.drawerPaper }}
        anchor="left"
      >
        <div>
          <Typography variant="h5" className={classes.title}>
            <ListItem button onClick={() => navigate('/dashboard')} >
              <ListItemIcon>
                <DashboardIcon color="primary" />
              </ListItemIcon>
              Dashboard
            </ListItem>
          </Typography>
        </div>

        {/* links/list section */}
        <List>
          {profile.role === 'ROLE_ADVISOR' ? advisorMenus.map((item) => (
            <ListItem
              button
              key={item.text}
              onClick={() => navigate(item.path)}
              className={location.pathname === item.path ? classes.active : null}
            >

              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItem>
          )) : clientMenus.map((item) => (
            <ListItem
              button
              key={item.text}
              onClick={() => navigate(item.path)}
              className={location.pathname === item.path ? classes.active : null}
            >

              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItem>
          ))}
        </List>

      </Drawer>

      {/* main content */}
      <div className={classes.page}>
        <div className={classes.toolbar}></div>
        <ToastContainer />
        <Outlet />
      </div>
    </div>
  )
}
export default Layout;