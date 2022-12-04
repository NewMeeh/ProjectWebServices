import { Layout, Menu, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useState, useEffect} from "react";
import {getToken} from './index'
import { useNavigate } from 'react-router-dom';
import MenuBar from './MenuBar'
const { Header, Footer, Content } = Layout;

export const Profile = () => {

    const [usr, setUsr] = useState(0);

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/login");
        }
    });

    const onClick = (e) => {
        navigate("/"+e.key);
    };

    return (
        <Layout>
            <MenuBar/>
            <Layout>
                <Content>
                    <Divider orientation="left">Info</Divider>
                    <div style={{'padding':'10px 20px 10px 20px'}}>
                        <UsrList />
                    </div>
                    <Divider orientation="left">My Bikes</Divider>
                    <Row gutter={[16, 16]} justify="space-between" align="middle">
                        <BikeList request={"/me/share"} type={2}/>
                    </Row>
                    <Divider orientation="left">Rented Bikes</Divider>
                    <Row gutter={[16, 16]} justify="space-between" align="middle">
                        <BikeList request={"/me/rent"} type={3}/>
                    </Row>
                </Content>
            </Layout>
            <Footer><Divider orientation="right">UGEBikes3000 all rights reserved</Divider></Footer>
        </Layout>
    );
}

export class UsrList extends React.Component {

    constructor() {
        super();
        this.state = {
            usr: null,
        }
    }

    componentDidMount() {
        const that = this;
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('token') },
        };
        fetch('http://localhost:1100/bikes/me', requestOptions)
            .then(response => response.json())
            .then(data => {
                that.setState({usr: data});
            });
    }

    render() {
        const rq = this.state.usr;
        let itemList = [];
        if (rq == null) {
            return (
                <h2></h2>
            );
        }
        else {
            return (
                <div>
                    <h2>{rq.username}</h2>
                    <h3>{rq.lname} {rq.fname}</h3>
                    <p>{rq.mail}</p>
                </div>
            );
        }
    }
}
