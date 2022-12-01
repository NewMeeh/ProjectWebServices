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
                cover={<img alt="Bike" src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"/>}
            >
                <Meta title={title} description={this.state.rating + " stars"}/>
                <p className="price">{this.state.price + "$"}</p>
            </Card>
            </Col>
        );
    }
}

export class BikeList extends React.Component {

    createCard (index, item){
        return <Bike key={index} owner={item.owner} price={item.price} rating={item.rating}/>;
    }

    render() {
        const rq = JSON.parse('{"Bikes":[{"owner":"Thomas", "rating":5, "price":15},{"owner":"Julien", "rating":3, "price":25},{"owner":"Adrien", "rating":4, "price":10},{"owner":"Julien", "rating":5, "price":15},{"owner":"Julien", "rating":5, "price":15},{"owner":"Julien", "rating":5, "price":15},{"owner":"Julien", "rating":5, "price":15},{"owner":"Julien", "rating":5, "price":15}]}')
        let itemList=[];
        rq.Bikes.forEach((item, index)=>{
            itemList.push( this.createCard(index, item) )
            })
        return(
            itemList
        );
    }
}