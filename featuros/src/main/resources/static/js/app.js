var Deployments = React.createClass({
        loadDeployments: function () {
            $.ajax({
                url: this.props.url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    var deployments = data._embedded.deployments;
                    this.prepareData(deployments);
                }.bind(this),
                error: function (xhr, status, err) {
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        },
        prepareData: function (deployments) {
            var products = [], groups = [];
            deployments.forEach(function (deployment) {
                if (groups.indexOf(deployment.group) === -1) {
                    groups.push(deployment.group);
                }
                if (products.indexOf(deployment.product) === -1) {
                    products.push(deployment.product);
                }
            });
            this.setState({deployments: deployments, allDeployments: deployments, products: products, groups: groups});
        },
        getInitialState: function () {
            return {
                query: '',
                deployments: [],
                allDeployments: [],
                products: [],
                selectedProduct: "",
                groups: [],
                selectedGroup: ""
            };
        },
        componentDidMount: function () {
            this.loadDeployments();
        },
        queryChanged: function (query) {
            this.filterBy(query, this.state.selectedProduct, this.state.selectedGroup)
        },
        productChanged: function (product) {
            this.setState({selectedProduct: product});
            this.filterBy(this.state.query, product, this.state.selectedGroup)
        },
        groupChanged: function (group) {
            this.setState({selectedGroup: group});
            this.filterBy(this.state.query, this.state.selectedProduct, group);
        },
        filterBy: function (query, product, group) {
            var self = this;
            var filteredDeployments = this.state.allDeployments
                .filter(function (deployment) {
                    return deployment.name.indexOf(query) > -1;
                }).filter(function (deployment) {
                    return product.length === 0 || deployment.product === product;
                }).filter(function (deployment) {
                    return group.length === 0 || deployment.group === group;
                });
            this.setState({query: query, deployments: filteredDeployments});
        },
        render: function () {
            return (
                <div>
                    <div className="row">
                        <div className="col-sm-12">
                            <form className="form-inline">
                                <fieldset>
                                    <legend>Deployments filter</legend>
                                    <SearchBox query={this.state.query} doSearch={this.queryChanged} label="Name"/>
                                    <SimpleSelect value={this.state.selectedProduct} options={this.state.products}
                                                  onChange={this.productChanged}
                                                  label="Product"/>
                                    <SimpleSelect value={this.state.selectedGroup} options={this.state.groups} onChange={this.groupChanged}
                                                  label="Group"/>
                                </fieldset>
                            </form>
                        </div>
                    </div>
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
        return (<div id="deployment-list" className="row">{deployments}</div>);
    }
});

var Deployment = React.createClass({
    render: function () {
        return (
            <div className="col-sm-4">
                <ul className="list-group">
                    <li className="list-group-item active">
                        <h3 className="panel-title">{this.props.data.name}</h3>
                    </li>
                    <li className="list-group-item">{this.props.data.product}</li>
                    <li className="list-group-item">{this.props.data.group}</li>
                    <li className="list-group-item">{this.props.data.version}</li>
                    <li className="list-group-item">{this.props.data.date}</li>
                </ul>
            </div>
        );
    }
});

var SimpleSelect = React.createClass({
    onChange: function (event) {
        this.props.onChange(event.target.value);
    },
    render: function () {
        var options = [(<Option key="empty" value="" label=""/>)];
        options.push(this.props.options.map(function (option) {
            return (
                <Option key={option} value={option} label={option}/>
            );
        }));
        return (
            <div className="form-group col-sm-4">
                <label htmlFor={this.props.label}>{this.props.label}</label>
                <select className="form-control" id={this.props.label} value={this.props.value} onChange={this.onChange}>
                    {options}
                </select>
            </div>
        );
    }
});
var Option = React.createClass({
    render: function () {
        return <option value={this.props.value}>{this.props.label}</option>
    }
});

var SearchBox = React.createClass({
    doSearch: function (event) {
        this.props.doSearch(event.target.value);
    },
    render: function () {
        return (
            <div className="form-group col-sm-4">
                <label htmlFor="search-query">{this.props.label}</label>
                <input type="text" id="search-query"
                       value={this.props.query} onChange={this.doSearch} placeholder="Search Name" className="form-control"/>
            </div>
        )
    }
});

React.render(
    <Deployments url="/api/deployments"/>,
    document.getElementById('content')
);