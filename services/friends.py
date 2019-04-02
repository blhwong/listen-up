import json
from flask import Flask
from werkzeug.exceptions import NotFound

from services import jsonify, root

app = Flask(__name__)

with open('{}/services/data/friends.json'.format(root()), 'r') as f:
    friends = json.load(f)

@app.route('/', methods=['GET'])
@jsonify
def friend_index():
    return {
        'uri': '/',
        'subresource_uris': {
            'users': '/friends',
            'friends': '/friends{/username}',
        }
    }


@app.route('/friends', methods=['GET'])
@jsonify
def friend_nodes():
    result = {
        'friends': [],
        'uri': '/friends',
    }
    for friend in friends.iterkeys():
        result['friends'].append({
            'username': friend,
            'uri': '/friends/{}'.format(friend),
        })
    result['uri'] = '/friends'
    return result


@app.route('/friends/<username>', methods=['GET'])
@jsonify
def friend_list(username):
    if username not in friends:
        raise NotFound('{} not found'.format(username))

    result = {
        'friends': friends[username],
        'uri': '/friends/{}'.format(username),
    }

    return result



if __name__ == '__main__':
    app.run(port=8000, debug=True)
