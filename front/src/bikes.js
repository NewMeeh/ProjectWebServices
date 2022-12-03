import { Card, Col } from 'antd';
import React from 'react';
const { Meta } = Card;

export class Bike extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            owner: props.owner,
            price: props.price,
            rating: props.rating,
        };
    }

    render() {
        const title = this.state.owner + "'s bike";
        return (
            <Col span={6}>
            <Card
                hoverable
                style={{width: 240, margin: "auto"}}
                cover={<img alt="Bike" src="https://i0.wp.com/goodcobikeclub.com/wp-content/uploads/2020/07/qi-bin-w4hbafegiac-unsplash.jpg?ssl=1"/>}
            >
                <Meta title={title} description={this.state.rating + " stars"}/>
                <p className="price">{this.state.price + "$"}</p>
            </Card>
            </Col>
        );
    }
}

export class BikeList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: props,
        };
    }

    createCard (index, item){
        return <Bike key={index} owner={item.owner} price={item.price} rating={item.rating} owned={item.rented}/>;
    }

    render() {
        const rq = JSON.parse(this.props.data);
        let itemList=[];
        rq.forEach((item, index)=>{
            itemList.push( this.createCard(index, item) )
            })
        return(
            itemList
        );
    }
}