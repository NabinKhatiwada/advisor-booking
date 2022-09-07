import React, { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { DataGrid } from '@mui/x-data-grid';
import DatePicker from 'react-datepicker';
import addDays from 'date-fns/addDays'
import './Client.css';
import { dateToStr } from '../../../utils/Utils';
import { bookAppointment, getUpcomingClientAppointments, showSuccessToast } from '../../../services/api';

const Client = () => {
  /* For List of appointments */
  const [appointments, setAppointments] = useState([]);
  const [paginationInfo, setPaginationInfo] = useState(null);
  const getClientAppointments = async () => {
    var list = await getUpcomingClientAppointments();
    setAppointments(list.appointments);
    setPaginationInfo(list.paginationInfo);
  };
  useEffect(() => {
    getClientAppointments();
  }, []);

  const handleBooking = async (id) => {
     let status = await bookAppointment(id);
     if(status == 200){
        showSuccessToast("Appointment Booked Successfully");
     }
     getClientAppointments();
  };
  const columns = [
    { field: 'appointmentId', headerName: 'Id', width: 50 },
    { field: 'appointmentDatetimeStr', headerName: 'Datetime', width: 160 },
    { field: 'appointmentStatus', headerName: 'Booking Status', width: 150 },
    { field: 'name', headerName: 'Advisor Name', width: 160 },
    { field: 'email', headerName: 'Advisor Email', width: 250 },
    {
      field: 'action',
      headerName: 'Action',
      width: 200,
      sortable: false,
      renderCell: (params) => {
        const onBookClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking
          var id = params.row.appointmentId;
          handleBooking(id);
          return;
        };
        if(params.row.appointmentStatus == 'AVAILABLE'){
        return <>
          <div className='row'>
            <div className='col-6'>
              <button className="btn btn-success" onClick={onBookClick}>Book</button>
            </div>
          </div>
        </>;
        }else{
          return <></>
        }
      },
    }
  ];

  return (<>
    <div style={{ minWidth: '600px', maxWidth: '80%' }}>

      <div className="row">
        <h4 className="text-left">List of Appointments</h4>
      </div>
      <div className="row mt-1" style={{ height: '500px' }}>
        {appointments && paginationInfo &&
          <DataGrid
            rows={appointments}
            columns={columns}
            pageSize={paginationInfo.total}
            getRowId={(row) => row.appointmentId}
          />
        }
      </div>
    </div>
  </>);
}
export default Client;

