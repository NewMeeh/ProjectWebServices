import { Card, Col, Button, Modal } from 'antd';
import React, { useState } from 'react';
const { Meta } = Card;

export class Bike extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type,
            item: props.item,
            owner: props.item.ownerName,
            price: props.item.locationPrice,
            rating: props.item.avgGrade,
            name: props.item.bikeName
        };
        this.removeBike = this.removeBike.bind(this);
        this.returnBike = this.returnBike.bind(this);
    }

    removeBike(){
        const that = this;
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('etoken') },
        };
        console.log("this is "+ that.state);
        fetch('http://localhost:1100/bikes/'+this.state.item.bikeId, requestOptions)
            .then(response => response.text())
            .then(data => {
                window.location.reload(false);
            });
    }

    returnBike(){
        const that = this;
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('etoken') },
            body: this.state.item.bikeId
        };
        console.log("this is "+ that.state);
        fetch('http://localhost:1100/bikes/turnIn', requestOptions)
            .then(response => response.text())
            .then(data => {
                window.location.reload(false);
            });
    }

    render() {
        const title = this.state.owner + "'s " + this.state.name ;
        if (this.state.type === 1)
        return (
            <Col span={6}>
                <Card
                    hoverable
                    style={{width: 240, margin: "auto"}}
                    cover={<img alt="Bike" src="https://i0.wp.com/goodcobikeclub.com/wp-content/uploads/2020/07/qi-bin-w4hbafegiac-unsplash.jpg?ssl=1"/>}
                >
                    <Meta title={title} description={this.state.rating + " stars"}/>
                    <p className="price">{this.state.price + "$"}</p>
                    <BikeDesc item={this.state.item}></BikeDesc>
                </Card>
            </Col>
        );
        if (this.state.type === 2)
            return (
                <Col span={6}>
                    <Card
                        hoverable
                        style={{width: 240, margin: "auto"}}
                        cover={<img alt="Bike" src="https://i0.wp.com/goodcobikeclub.com/wp-content/uploads/2020/07/qi-bin-w4hbafegiac-unsplash.jpg?ssl=1"/>}
                    >
                        <Meta title={title} description={this.state.rating + " stars"}/>
                        <p className="price">{this.state.price + "$"}</p>
                        <Button type="primary" onClick={this.removeBike}>Remove</Button>
                    </Card>
                </Col>
            );
        if (this.state.type === 3)
            return (
                <Col span={6}>
                    <Card
                        hoverable
                        style={{width: 240, margin: "auto"}}
                        cover={<img alt="Bike" src="https://i0.wp.com/goodcobikeclub.com/wp-content/uploads/2020/07/qi-bin-w4hbafegiac-unsplash.jpg?ssl=1"/>}
                    >
                        <Meta title={title} description={this.state.rating + " stars"}/>
                        <p className="price">{this.state.price + "$"}</p>
                        <Button type="primary" onClick={this.returnBike}>Remove</Button>
                    </Card>
                </Col>
            )
    }
}


export class BikeList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            key: props.key,
            type: props.type,
            request: props.request,
            itemList: null,
        }
    }

    componentDidMount() {
        const that = this;
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('etoken') },
        };
        fetch('http://localhost:1100/bikes'+this.state.request, requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.error !== undefined)
                    that.setState({itemList: null});
                else
                    that.setState({itemList: data});
            });
    }

    createCard (index, item, type){
        return <Bike key={index} item={item} type={type}/>;
    }

    render() {
        const rq = this.state.itemList;
        let itemList=[];
        if (rq != null) {
            rq.forEach((item, index) => {
                itemList.push(this.createCard(index, item, this.state.type))
            })
        }
        return(
            itemList
        );
    }
}

const BikeDesc = (props) => {
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);
    const showModal = () => {
        setOpen(true);
    };
    const handleOk = () => {
        setLoading(true);
        setTimeout(() => {
            setLoading(false);
            setOpen(false);
        }, 3000);
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', "token":localStorage.getItem('etoken') },
            body: props.item.bikeId
        };
        fetch('http://localhost:1100/bikes/rent', requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data);

            });
    };
    const handleCancel = () => {
        setOpen(false);
    };
    const title = props.item.ownerName + "'s " + props.item.bikeName ;

    return (
        <>
            <Button type="primary" onClick={showModal}>
                More
            </Button>
            <Modal
                open={open}
                title={title}
                onOk={handleOk}
                onCancel={handleCancel}
                footer={[
                    <Button key="back" onClick={handleCancel}>
                        Return
                    </Button>,
                    <Button key="rent" loading={loading} onClick={handleOk}>
                        {props.item.rented === false ? "Rent" : "WaitingList"}
                    </Button>,
                ]}
            >
                <p>{props.item.description}</p>
                <br/>
                <p>Disponible : {props.item.rented === false ? "Oui" : "Non"}</p>
                <p>Prix : {props.item.locationPrice}$</p>
                <p>Note : {props.item.avgGrade} stars ({props.item.grades.length} avis)</p>
            </Modal>
        </>
    );
};
export default BikeDesc;