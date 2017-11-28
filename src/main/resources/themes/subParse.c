      {% loop in $fields %}
      //{$comment}
      updateCANData({$pos}, {$len})
      {% onEmpty %}
      {% endloop %}