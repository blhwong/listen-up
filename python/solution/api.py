from flask import Flask
from services import jsonify

app = Flask(__name__)


@app.route('/', methods=['GET'])
@jsonify
def index():
    return {
        'uri': '/',
        'subresource_uris': {
            'users': '/users',
            'user': '/users{/username}',
            'trending': '/users{/username}/trending',
        }
    }


@app.route('/users', methods=['GET'])
@jsonify
def user_list():
    """
    TODO: Implement this endpoint
    Return a list of users, each response should include the following
    information

    {
        "uri": "/users",
        "users": [
            {
                "username": "joe_example",
                "plays": 178,
                "friends": 7,
                "uri": "/users/joe_example"
            },
            ...
        ]
    }

    :return: User list
    """
    return 'Not Implemented', 500


@app.route('/users/<username>', methods=['GET'])
@jsonify
def user_instance(username):
    """
    TODO: Implement this endpoint
    Return a user instance, each user record should include the following

    {
        "username": "joe_example",
        "plays": 178,
        "friends": 7,
        "uri": "/users/joe_example",
        "subresource_uris": {
            "trending": "/users/joe_example/trending",
        }
    }

    :param username:
    :return:
    """
    return 'Not Implemented', 500


@app.route('/users/<username>/trending', methods=['GET'])
@jsonify
def user_trending(username):
    """
    TODO: Implement this endpoint

    {
        "tracks": [
            "E75C38C1-E2BB-BAF6-620B-9255D035B693",
            "B3CA64C2-7A52-FD9A-3252-2A2FB7AD43C1",
            ... snip additional tracks ...
        ]
        "uri": "/users/joe_example/trending"
    }

    :param username:
    :return:
    """
    return 'Not Implemented', 500

app.run(debug=True, port=8005)
