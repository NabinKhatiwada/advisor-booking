import React, { useEffect, useState } from 'react';
import { getAdvisors, showFailureToast, getMyChats, getClients, getProfile, showSuccessToast, sendChatMessage } from '../../../services/api';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import './Chat.css';
import { useForm } from 'react-hook-form';

const Chat = () => {
    const [profile, setProfile] = useState(null);
    // const [users, setUsers] = useState([]);

    const [options, setOptions] = useState([]);

    const getMe = async (token) => {
        let profile = await getProfile(token);
        setProfile(profile);
        var userResponse;
        if (profile.role === 'ROLE_CLIENT') {
            userResponse = await getAdvisors();
        } else {
            userResponse = await getClients();
        }
        // setUsers(userResponse.users);

        // let list = users.map(x => ({ value: x.userId, label: x.name }));
        var items = [];
        userResponse.users.forEach(user => {
            items.push({ value: user.userId, label: user.name });
        })
        setOptions(items);


    }

    useEffect(() => {
        let token = localStorage.getItem('token');
        getMe(token);
    }, []);




    /* Chat Form */
    const { register, setValue, handleSubmit, formState: { errors }, reset } = useForm();
    const [nextPersonId, setNextPersonId] = useState(null);
    const [chats, setChats] = useState([]);
    const onSelect = (option) => {
        setNextPersonId(option.value);
        getChats(option.value);
    };

    const getChats = async (nextUserId) => {
        let chatResponse = await getMyChats(nextUserId);
        let chatList = chatResponse.chats;
        setChats(chatList);

        console.log('chat data are ', chatList);
    }

    const sendMessage = async (data) => {
        let statusCode = await sendChatMessage(data);
        if (statusCode == 200) {
            showSuccessToast("Message Sent");
        }
        getChats(nextPersonId);
        reset();

    }

    const onSubmit = (data) => {
        if (nextPersonId === null) {
            showFailureToast("Please select a Message Receiver ");
            return;
        }
        var chatData = { receiverId: nextPersonId, message: data.message };
        sendMessage(chatData);
    };


    return (
        <div>
            {profile && options.length > 0 &&
                <div style={{ minWidth: '400px', maxWidth: '25%' }}>
                    <form onSubmit={handleSubmit(onSubmit)} >
                        {profile.role === 'ROLE_ADVISOR' ? <Dropdown onChange={onSelect} options={options} placeholder="Select Client" />
                            : <Dropdown onChange={onSelect} options={options} placeholder="Select Advisor" />}

                        <div>
                            <textarea className="form-control mt-2" rows="3" placeholder="Hello.." style={{ width: '100%' }}
                                {...register('message', { required: true })}></textarea>
                            {errors.message && errors.message.type === "required" && <span style={{ color: 'red' }}>Message is requied</span>}
                        </div>
                        <button type="submit" className="btn btn-primary mt-2">Send Message</button>
                    </form>

                    {chats != null && chats.length > 0 && <>

                        {chats.map((chat) =>
                            <div key={chat.chatId}>

                                {
                                    chat.senderId == profile.userId ?
                                        <div className="container">
                                            {/* <img src={chat.imageUrl} alt="Avatar" style={{ width: '100%' }} /> */}
                                            
                                            <p>{chat.message}</p>
                                            <span className="time-right">{chat.messageDatetimeStr} </span>
                                        </div>
                                        : <div className="container darker">
                                            {/* <img src={chat.imageUrl} alt="Avatar" className="right" style={{ width: '100%' }} /> */}
                                            <p>{chat.message}</p>
                                            <span className="time-left">{chat.messageDatetimeStr} </span>
                                        </div>

                                }

                            </div>
                        )}

                    </>}

                    {/* {chats != null && chats.length > 0 && <>
                        {chats.map(chat => {
                            {
                                chat.senderId === profile.userId ?
                                <div className="container">
                                    <img src={chat.imageUrl} alt="Avatar" style={{ width: '100%' }} />
                                    <p>{chat.message}</p>
                                    <span className="time-right">11:00</span>
                                </div>
                                : <div className="container darker">
                                    <img src={chat.imageUrl} alt="Avatar" className="right" style={{ width: '100%' }} />
                                    <p>{chat.message}</p>
                                    <span className="time-left">11:01</span>
                                </div>

                            }


                        })}
                    </>} */}

                </div>
            }
        </div>
    );
}
export default Chat;