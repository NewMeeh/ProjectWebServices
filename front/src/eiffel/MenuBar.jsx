import { Layout, Menu} from 'antd';
import 'antd/dist/reset.css';
import "./index.css";
import React from "react";
import { useNavigate } from 'react-router-dom';

const { Header } = Layout;
export const EMenuBar = () => {

    const navigate = useNavigate();
    const onClick = (e) => {
        navigate(""+e.key);
    };

    return(
    <Header>
        <div className="logo">UGEBikes3000</div>
        <Menu
            onClick={onClick}
            theme="dark"
            mode="horizontal"
            items={[{label: 'Accueil', key: '/eiffel/'}, {label: 'Profile', key: '/eiffel/profile'}, {label: "Externes", key:'/gustave/'}]}
        />
    </Header>
    );
}
export default EMenuBar;