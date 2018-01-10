import React, {PropTypes} from 'react'

import Model from 'components/Model'
import Settings from 'Settings'

export default class Task extends Model {
	static contextTypes = {
		app: PropTypes.object.isRequired,
	}

	static resourceName = 'Task'
	static displayName() {
		return Settings.TASK.shortLabel
	}

	static listName = 'taskList'

	static schema = {
		shortName: '',
		longName: '',
		category: '',
		responsibleOrg: {},
		parentTask: {},
		childrenTasks: [],
		customFieldEnum: '',
		customField: '',
		projectedCompletion: null,
		plannedCompletion: null,
	}

	static autocompleteQuery = "id, shortName, longName"

	static autocompleteTemplate(task) {
		return <span>{[task.shortName, task.longName].join(' - ')}</span>
	}

	toString() {
		return this.longName || this.shortName || 'Unnamed'
	}
}
