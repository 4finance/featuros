var Deployments = React.createClass({
        loadDeployments: function () {
            $.ajax({
                url: this.props.url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    var deployments = data._embedded.notifications;
                    this.setState({deployments: deployments, allDeployments: deployments});
                }.bind(this),
                error: function (xhr, status, err) {
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        },
        getInitialState: function () {
            return {
                query: '',
                deployments: [],
                allDeployments: []
            };
        },
        componentDidMount: function () {
            this.loadDeployments();
        },
        doSearch: function (query) {
            var allDeployments = this.state.allDeployments;
            if (query.length > 0) {
                var filteredDeployments = allDeployments.filter(function (deployment) {
                    return deployment.name.indexOf(query) > -1;
                });
                this.setState({query: query, deployments: filteredDeployments});
            } else {
                this.setState({query: query, deployments: allDeployments});
            }
        },
        render: function () {
            return (
                <div>
                    <div><SearchBox query={this.state.query} doSearch={this.doSearch}/></div>
                    <DeploymentList data={this.state.deployments}/>
                </div>
            )
        }
    })
    ;

var DeploymentList = React.createClass({
    render: function () {
        var deployments = this.props.data.map(function (deployment) {
            return (
                <Deployment key={deployment.name} data={deployment}/>
            );
        });
        return (<div id="deployment-list">{deployments}</div>);
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

var SearchBox = React.createClass({
    doSearch: function () {
        var query = this.refs.searchInput.getDOMNode().value; // this is the search text
        this.props.doSearch(query);
    },
    render: function () {
        return <input type="text" ref="searchInput" placeholder="Search Name" value={this.props.query} onChange={this.doSearch}/>
    }
});

React.render(
    <Deployments url="/api/notifications"/>,
    document.getElementById('content')
);