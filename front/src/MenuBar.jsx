import { Layout, Menu} from 'antd';
import 'antd/dist/reset.css';
import "./index.css";
import React, {useState, useEffect} from "react";
import { useNavigate } from 'react-router-dom';

const { Header, Footer, Content } = Layout;
export const MenuBar = () => {

    const navigate = useNavigate();
    const onClick = (e) => {
        navigate("/"+e.key);
    };

    return(
    <Header>
        <div className="logo">UGEBikes3000</div>
        <Menu
            onClick={onClick}
            theme="dark"
            mode="horizontal"
            items={[{label: 'Acceuil', key: ''}, {label: 'Profile', key: 'profile'},{label: 'Panier', key: 'Panier'}]}
        />
    </Header>
    );
}
export default MenuBar;