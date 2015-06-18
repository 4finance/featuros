var Deployments = React.createClass({
    loadDeployments: function () {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    getInitialState: function () {
        return {
            data: {
                _embedded: {
                    notifications: [
                        {name: "integration-post-lt"},
                        {name: "backoffice"}
                    ]
                }
            }
        };
    },
    componentDidMount: function () {
        this.loadDeployments();
    },
    render: function () {
        return <DeploymentList data={this.state.data._embedded.notifications}/>
    }
});

var DeploymentList = React.createClass({
    render: function () {
        var deployments = this.props.data.map(function (deployment) {
            return (
                <Deployment key={deployment.name} data={deployment}/>
            );
        });
        return (<div>{deployments}</div>);
    }
});

var Deployment = React.createClass({
    render: function () {
        return (
            <ul className="list-group">
                <li className="list-group-item active">
                    <h3 className="panel-title">{this.props.data.name}</h3>
                </li>
                <li className="list-group-item">{this.props.data.product}</li>
                <li className="list-group-item">{this.props.data.group}</li>
                <li className="list-group-item">{this.props.data.version}</li>
                <li className="list-group-item">{this.props.data.date}</li>
            </ul>
        );
    }
});

React.render(
    <Deployments url="/api/notifications"/>,
    document.getElementById('content')
);