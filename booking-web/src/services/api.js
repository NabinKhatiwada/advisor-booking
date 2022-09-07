import axios from "axios";
import { API_BASE_URL } from '../constants/index';
import { toast } from 'react-toastify';

export const getProfile = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + "/profile", { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      // console.log(res.data);
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
};

const showToast = (msg) => {
  toast(msg, {
    position: toast.POSITION.TOP_CENTER
  })
};

export const createAppointment = async (data) => {
  let localToken = localStorage.getItem('token');
  return axios.post(API_BASE_URL + "/appointment", data, { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.status;

    }).catch(e => {
      console.log("exception ", e);
    });
}
export const showSuccessToast = (msg) => {
  showToast(msg)
}

export const showFailureToast = (msg) => {
  showToast(msg)
}

export const getUpcomingAppointments = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + "/appointment", { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const getUpcomingClientAppointments = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + "/appointmentClient", { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const bookAppointment = async (appointmentId) => {
  let localToken = localStorage.getItem('token');
  return axios.post(API_BASE_URL + /bookAppointment/ + appointmentId, {}, { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.status;

    }).catch(e => {
      console.log("exception ", e);
    });
}

export const getMyBookings = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + '/getMyBookings', { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const cancelBooking = async (userAppointmentId) => {
  let localToken = localStorage.getItem('token');
  return axios.delete(API_BASE_URL + '/deleteAppointment/' + userAppointmentId, { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const getAdvisors = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + '/advisors', { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const getClients = async () => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + '/clients', { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const sendChatMessage = async (data) => {
  let localToken = localStorage.getItem('token');
  return axios.post(API_BASE_URL + '/chat', data, { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.status;
    }).catch(e => {
      console.log("exception ", e);
    });
}

export const getMyChats = async (nextUserId) => {
  let localToken = localStorage.getItem('token');
  return axios.get(API_BASE_URL + '/chat/' + nextUserId, { headers: { "Authorization": `Bearer ${localToken}` } })
    .then(res => {
      return res.data;
    }).catch(e => {
      console.log("exception ", e);
    });
}