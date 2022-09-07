import React, { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { DataGrid } from '@mui/x-data-grid';
import DatePicker from 'react-datepicker';
import addDays from 'date-fns/addDays'
import './Advisor.css';
import { dateToStr } from '../../../utils/Utils';
import { createAppointment, getUpcomingAppointments,showSuccessToast } from '../../../services/api';

const Advisor = () => {
  const { control, register, setValue, handleSubmit, formState: { errors }, reset } = useForm();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const handleDateChange = (date) => {
    setSelectedDate(date);
  };
  const onSubmit = async (data) => {
    var formattedDate = dateToStr(selectedDate);

    console.log('formatted ', formattedDate);
    var data = { appointmentDatetimeStr: formattedDate };
    var status = await createAppointment(data);
    if(status == 200){
      showSuccessToast('Appointment Created Successfully');
    }
    await getAdvisorAppointments();
  };

  /* For List of appointments */
  const [appointments, setAppointments] = useState([]);
  const [paginationInfo,setPaginationInfo] = useState(null);
  const getAdvisorAppointments = async() => {
    var list = await getUpcomingAppointments();
    console.log("list = ",list    )
    setAppointments(list.appointments);
    setPaginationInfo(list.paginationInfo);
  };
  useEffect(() => {
    getAdvisorAppointments();
   
  }, []);

  const columns = [
    { field: 'appointmentId', headerName: 'Id', width: 50 },
    { field: 'appointmentDatetimeStr', headerName: 'Datetime', width: 160 },
    { field: 'appointmentStatus', headerName: 'Booking Status', width: 150 },
    { field: 'name', headerName: 'Client Name', width: 160 },
    { field: 'email', headerName: 'Client Email', width: 200 },
  ];

  return (<>
    <div style={{ minWidth: '400px', maxWidth: '50%' }}>

      <div className="row mb-3">
        <h4 className="text-left"> Schedule Appointment </h4>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} >
        <div className="mb-3">
          <label htmlFor="Material Date Picker" className="form-label">Select Date & Time</label>


          <DatePicker
            selected={selectedDate}
            onChange={handleDateChange}
            showTimeSelect
            timeFormat="HH:mm"
            timeIntervals={30}
            timeCaption="time"
            dateFormat="dd MMM yyyy h:mm a"
            minDate={new Date()}
            maxDate={addDays(new Date(), 365)}

          />
        </div>

        <button type="submit" className="btn btn-primary">Schedule</button>
      </form>

      <div className="row mt-5 mb-3">
        <hr />
      </div>

      <div className="row">
        <h4 className="text-left">List of Upcoming Appointments</h4>
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
export default Advisor;

