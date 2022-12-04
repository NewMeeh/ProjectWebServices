import { Layout, Row} from 'antd';
import { Divider } from 'antd';
import {BikeList} from './bikes'
import 'antd/dist/reset.css';
import "./index.css";
import React, {useEffect} from "react";
import {getToken} from './Main'
import { useNavigate } from 'react-router-dom';
import MenuBar from './MenuBar'
const {Footer, Content } = Layout;

export const GProfile = () => {

    const navigate = useNavigate();
    useEffect(() => {
        if (!getToken()) {
            navigate("/gustave/login");
        }
    });

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
            headers: { 'Content-Type': 'application/json', "gtoken":localStorage.getItem('gtoken') },
        };
        fetch('http://localhost:1100/bikes/me', requestOptions)
            .then(response => response.json())
            .then(data => {
                that.setState({usr: data});
            });
    }

    render() {
        const rq = this.state.usr;
        if (rq == null) {
            return (
                <></>
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
