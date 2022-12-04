import 'antd/dist/reset.css';
import "./index.css";
import React from "react";
import { Button, Checkbox, Form, Input } from 'antd';
import { useNavigate } from "react-router-dom";

export const ELogin = () => {

    const navigate = useNavigate();
    const handleRegisterClick = () => navigate("/eiffel/register", { replace: true });

    const onFinish = (values: any) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin":"*" },
            body: JSON.stringify({ username: values.username, password: values.password})
        };
        fetch('http://localhost:1080/uge/login', requestOptions)
            .then(response => response.text())
            .then(data => {
                if (data.localeCompare("") !== 0) {
                    localStorage.setItem('etoken', data)
                    navigate("/eiffel/", { replace: true });
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

                <Form.Item name="remember" valuePropName="checked" wrapperCol={{ offset: 8, span: 16 }}>
                    <Checkbox>Remember me</Checkbox>
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

export const Register = () => {

    const navigate = useNavigate();
    const handleLoginClick = () => navigate("/eiffel/login", { replace: true });

    const onFinish = (values: any) => {
        console.log('Success:', values);
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
                    label="First Name"
                    name="fname"
                    rules={[{ required: true, message: 'Please input your first name!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Last Name"
                    name="lname"
                    rules={[{ required: true, message: 'Please input your last name!' }]}
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