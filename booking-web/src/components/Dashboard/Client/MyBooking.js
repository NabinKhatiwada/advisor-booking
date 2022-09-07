import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { getMyBookings, cancelBooking, showSuccessToast } from '../../../services/api';

const MyBooking = () => {
    /* For List of appointments */
    const [appointments, setAppointments] = useState([]);
    const [paginationInfo, setPaginationInfo] = useState(null);
    const getClientAppointments = async () => {
        var list = await getMyBookings();
        setAppointments(list.appointments);
        setPaginationInfo(list.paginationInfo);
    };
    useEffect(() => {
        getClientAppointments();
    }, []);

    const handleCancellation = async (bookingId) => {
        let status = await cancelBooking(bookingId);
        if (status === 200) {
            showSuccessToast("Appointment Booked Successfully");
        }
        getClientAppointments();
    };
    const columns = [
        { field: 'userAppointmentId', headerName: 'Id', width: 50 },
        { field: 'appointmentDatetimeStr', headerName: 'Datetime', width: 160 },
        { field: 'userAppointmentStatus', headerName: 'Booking Status', width: 150 },
        { field: 'name', headerName: 'Advisor Name', width: 160 },
        { field: 'email', headerName: 'Advisor Email', width: 250 },
        {
            field: 'action',
            headerName: 'Action',
            width: 200,
            sortable: false,
            renderCell: (params) => {
                const onCancelClick = (e) => {
                    e.stopPropagation(); // don't select this row after clicking
                    var confirm = window.confirm("Are you sure?");
                    if (confirm) {
                        var id = params.row.userAppointmentId;
                        handleCancellation(id);
                    }
                    return;
                };
                if (params.row.userAppointmentStatus === 'CONFIRMED') {
                    return <>
                        <div className='row'>
                            <div className='col-6'>
                                <button className="btn btn-danger" onClick={onCancelClick}>CANCEL</button>
                            </div>
                        </div>
                    </>;
                } else {
                    return <></>
                }
            },
        }
    ];

    return (<>
        <div style={{ minWidth: '600px', maxWidth: '80%' }}>

            <div className="row">
                <h4 className="text-left">My Appointments</h4>
            </div>
            <div className="row mt-1" style={{ height: '500px' }}>
                {appointments && paginationInfo &&
                    <DataGrid
                        rows={appointments}
                        columns={columns}
                        pageSize={paginationInfo.total}
                        getRowId={(row) => row.userAppointmentId}
                    />
                }
            </div>
        </div>
    </>);
}
export default MyBooking;

