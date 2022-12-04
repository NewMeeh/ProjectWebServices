import 'antd/dist/reset.css';
import "./index.css";
import React from "react";
import { Button, Checkbox, Form, Input } from 'antd';
import { useNavigate } from "react-router-dom";

export const GLogin = () => {

    const navigate = useNavigate();
    const handleRegisterClick = () => navigate("/gustave/register", { replace: true });

    const onFinish = (values: any) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify({ username: values.username, password: values.password})
        };
        fetch('http://localhost:1090/gbs/login', requestOptions)
            .then(response => response.text())
            .then(data => {
                if (data.localeCompare("") !== 0) {
                    localStorage.setItem('gtoken', data)
                    navigate("/gustave/", { replace: true });
                }
            });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <div style={{"margin": "auto","width":" 50%","padding": "10px"}}>
            <h1 style={{'textAlign':'center'}}>UGEBIKE</h1>
            <Form
                name="basic"
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                initialValues={{ remember: true }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    label="Username"
                    name="username"
                    rules={[{ required: true, message: 'Please input your username!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Password"
                    name="password"
                    rules={[{ required: true, message: 'Please input your password!' }]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit">
                        Login
                    </Button>
                    <Button type="link" onClick={handleRegisterClick}>
                        Register
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );

};

export const GRegister = () => {

    const navigate = useNavigate();
    const handleLoginClick = () => navigate("/gustave/login", { replace: true });

    const onFinish = (values: any) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify({ username: values.username, password: values.password, email:values.email
                                    ,tel:values.tel, age:values.age})
        };
        fetch('http://localhost:1090/gbs/signIn', requestOptions)
            .then(response => response.text())
            .then(data => {
                if (data.localeCompare("") !== 0) {
                    navigate("/gustave/login", { replace: true });
                }
            });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <div style={{"margin": "auto","width":" 50%","padding": "10px"}}>
            <h1 style={{'text-align':'center'}}>UGEBIKE</h1>
            <Form
                name="basic"
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                initialValues={{ remember: true }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    label="Username"
                    name="username"
                    rules={[{ required: true, message: 'Please input your username!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Password"
                    name="password"
                    rules={[{ required: true, message: 'Please input your password!' }]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    label="Tel"
                    name="tel"
                    rules={[{ required: true, message: 'Please input your Telephone number!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Age"
                    name="age"
                    rules={[{ required: true, message: 'Please input your age!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Email"
                    name="email"
                    rules={[{ required: true, message: 'Please input your email!' }]}
                >
                    <Input />
                </Form.Item>


                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit">
                        Register
                    </Button>
                    <Button type="link" onClick={handleLoginClick}>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );

};