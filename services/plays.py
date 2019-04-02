import json
from flask import Flask
from werkzeug.exceptions import NotFound

from services import jsonify, root

app = Flask(__name__)

with open('{}/services/data/plays.json'.format(root()), 'r') as f:
    plays = json.load(f)

@app.route('/', methods=['GET'])
@jsonify
def play_index():
    return {
        'uri': '/',
        'subresource_uris': {
            'users': '/plays',
            'plays': '/plays{/username}',
        }
    }


@app.route('/plays', methods=['GET'])
@jsonify
def play_users():
    result = {
        'users': [],
        'uri': '/plays'
    }
    for user in plays.iterkeys():
        result['users'].append({
            'username': user,
            'uri': '/plays/{}'.format(user)
        })
    result['uri'] = '/plays'
    return result


@app.route('/plays/<username>', methods=['GET'])
@jsonify
def play_list(username):
    if username not in plays:
        raise NotFound('{} not found'.format(username))

    result = {
        'plays': plays[username],
        'uri': '/plays/{}'.format(username),
    }

    return result

if __name__ == '__main__':
    app.run(port=8001, debug=True)
